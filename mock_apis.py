#!/usr/bin/env python3
"""
mock_apis.py
============

Mock server(s) for the external APIs consumed by `poc-client`
(andes-api-toolkit/examples/poc-client), based on the OpenAPI contracts:

  - contracts/openapi-client-a.yaml  -> Orders API      (default port 8082)
  - contracts/openapi-client-b.yaml  -> Inventory API   (default port 8083)

No third-party dependencies required (uses only the Python standard library),
so it can run independently of Maven/Java for manual testing (curl, Postman,
poc-client, poc-integration, etc.).

NOTE: poc-client already ships its own in-process Java simulator for the
Orders API on port 8082 (ExternalOrdersSimulatorController). This script is
useful when you want to:
  - test the Inventory API (client-b), which has NO Java simulator yet, or
  - test Orders standalone, without starting the whole Spring Boot app
    (in that case run this script on a different port with --orders-port
    and point `andes.api.client.clients.orders.base-url` at it).

Usage
-----
    python3 mock_apis.py                      # both mocks, default ports (8082/8083)
    python3 mock_apis.py --only orders         # just the Orders mock
    python3 mock_apis.py --only inventory      # just the Inventory mock
    python3 mock_apis.py --orders-port 9082 --inventory-port 9083

Endpoints
---------
Orders API (Client A):
    GET  /v2/orders/{orderId}   -> 200 Order | 404 PartnerError
    POST /v2/orders             -> 201 Order | 422 PartnerError

Inventory API (Client B):
    GET  /stock/{sku}           -> 200 StockLevel | 404 InventoryFault
    GET  /stock/search          -> 200 StockSearchResult
        query params: query (optional), cursor (optional), limit (default 50)
"""

import argparse
import json
import threading
from datetime import datetime, timezone
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from itertools import count
from urllib.parse import urlparse, parse_qs

# ---------------------------------------------------------------------------
# In-memory data stores (seeded so a fresh GET works out of the box)
# ---------------------------------------------------------------------------

_orders_lock = threading.Lock()
_orders = {
    "ORD-1": {
        "orderId": "ORD-1",
        "customerId": 1,
        "totalAmount": 99.90,
        "status": "CONFIRMED",
        "items": [{"sku": "SKU-1", "quantity": 2, "unitPrice": 49.95}],
    }
}
_order_seq = count(2)

_stock_lock = threading.Lock()
_stock = {
    "SKU-1": {
        "sku": "SKU-1",
        "warehouse": "WH-LIMA-01",
        "quantityAvailable": 120,
        "lastUpdated": datetime.now(timezone.utc).isoformat(),
    },
    "SKU-2": {
        "sku": "SKU-2",
        "warehouse": "WH-LIMA-01",
        "quantityAvailable": 0,
        "lastUpdated": datetime.now(timezone.utc).isoformat(),
    },
}


def _now_iso() -> str:
    return datetime.now(timezone.utc).isoformat()


# ---------------------------------------------------------------------------
# Shared handler helpers
# ---------------------------------------------------------------------------

class _JsonHandler(BaseHTTPRequestHandler):
    """Base handler with small helpers for JSON in/out. Subclasses implement
    do_ROUTES() to keep routing logic per-API and readable."""

    server_version = "AndesMock/1.0"

    def log_message(self, fmt, *args):
        print(f"[{self.server.mock_name}] {self.address_string()} - {fmt % args}")

    def _send_json(self, status: int, payload: dict | list):
        body = json.dumps(payload).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def _read_json_body(self) -> dict:
        length = int(self.headers.get("Content-Length", 0) or 0)
        if length == 0:
            return {}
        raw = self.rfile.read(length)
        return json.loads(raw.decode("utf-8")) if raw else {}

    def do_GET(self):
        self._route("GET")

    def do_POST(self):
        self._route("POST")

    def _route(self, method: str):
        try:
            self.server.dispatch(self, method)
        except Exception as exc:  # pragma: no cover - safety net for the demo mock
            self._send_json(500, {"error": str(exc)})


# ---------------------------------------------------------------------------
# Orders API (contracts/openapi-client-a.yaml)
# ---------------------------------------------------------------------------

def _orders_dispatch(handler: _JsonHandler, method: str):
    parsed = urlparse(handler.path)
    parts = [p for p in parsed.path.split("/") if p]  # ['v2', 'orders', ...]

    if method == "GET" and len(parts) == 3 and parts[:2] == ["v2", "orders"]:
        order_id = parts[2]
        with _orders_lock:
            order = _orders.get(order_id)
        if order is None:
            handler._send_json(404, {
                "errorCode": "ORDER_NOT_FOUND",
                "errorMessage": f"Order {order_id} not found",
                "timestamp": _now_iso(),
            })
            return
        handler._send_json(200, order)
        return

    if method == "POST" and len(parts) == 2 and parts == ["v2", "orders"]:
        body = handler._read_json_body()
        items = body.get("items") or []
        if not body.get("customerId") or not items:
            handler._send_json(422, {
                "errorCode": "VALIDATION_ERROR",
                "errorMessage": "customerId and items are required",
                "timestamp": _now_iso(),
            })
            return
        with _orders_lock:
            order_id = f"ORD-{next(_order_seq)}"
            total = sum(i.get("unitPrice", 0) * i.get("quantity", 0) for i in items)
            order = {
                "orderId": order_id,
                "customerId": body["customerId"],
                "totalAmount": round(total, 2),
                "status": "PENDING",
                "items": items,
            }
            _orders[order_id] = order
        handler._send_json(201, order)
        return

    handler._send_json(404, {
        "errorCode": "NOT_FOUND",
        "errorMessage": f"No route for {method} {handler.path}",
        "timestamp": _now_iso(),
    })


# ---------------------------------------------------------------------------
# Inventory API (contracts/openapi-client-b.yaml)
# ---------------------------------------------------------------------------

def _inventory_dispatch(handler: _JsonHandler, method: str):
    parsed = urlparse(handler.path)
    parts = [p for p in parsed.path.split("/") if p]  # ['stock', ...]

    if method == "GET" and parts == ["stock", "search"]:
        qs = parse_qs(parsed.query)
        query = (qs.get("query") or [""])[0].lower()
        limit = int((qs.get("limit") or ["50"])[0])

        with _stock_lock:
            candidates = [
                item for item in _stock.values()
                if query in item["sku"].lower()
            ] if query else list(_stock.values())

        page = candidates[:limit]
        next_cursor = None if len(candidates) <= limit else "cursor-2"
        handler._send_json(200, {"results": page, "nextCursor": next_cursor})
        return

    if method == "GET" and len(parts) == 2 and parts[0] == "stock":
        sku = parts[1]
        with _stock_lock:
            item = _stock.get(sku)
        if item is None:
            handler._send_json(404, {
                "fault": {
                    "reason": "SKU_NOT_FOUND",
                    "detail": f"SKU {sku} not found",
                }
            })
            return
        handler._send_json(200, item)
        return

    handler._send_json(404, {
        "fault": {"reason": "NOT_FOUND", "detail": f"No route for {method} {handler.path}"}
    })


# ---------------------------------------------------------------------------
# Server bootstrap
# ---------------------------------------------------------------------------

class _MockServer(ThreadingHTTPServer):
    def __init__(self, address, dispatch, mock_name):
        super().__init__(address, _JsonHandler)
        self.dispatch = dispatch
        self.mock_name = mock_name


def _serve(name: str, port: int, dispatch):
    server = _MockServer(("0.0.0.0", port), dispatch, name)
    print(f"[{name}] listening on http://localhost:{port}")
    server.serve_forever()


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--only", choices=["orders", "inventory"], help="Run only one of the two mocks")
    parser.add_argument("--orders-port", type=int, default=8082, help="Port for the Orders API mock (default: 8082)")
    parser.add_argument("--inventory-port", type=int, default=8083, help="Port for the Inventory API mock (default: 8083)")
    args = parser.parse_args()

    threads = []
    if args.only in (None, "orders"):
        threads.append(threading.Thread(
            target=_serve, args=("orders", args.orders_port, _orders_dispatch), daemon=True))
    if args.only in (None, "inventory"):
        threads.append(threading.Thread(
            target=_serve, args=("inventory", args.inventory_port, _inventory_dispatch), daemon=True))

    for t in threads:
        t.start()

    print("Press Ctrl+C to stop.")
    try:
        for t in threads:
            t.join()
    except KeyboardInterrupt:
        print("\nStopping mock server(s).")


if __name__ == "__main__":
    main()
