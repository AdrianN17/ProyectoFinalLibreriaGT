package pe.andes.poc.client.orders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Local stand-in for the external "Orders API" described in
 * {@code contracts/openapi-client-a.yaml}, so this PoC can run without any real
 * network dependency. In a real deployment, {@code andes.api.client.clients.orders.base-url}
 * would simply point to the partner's actual base URL instead of this app.
 */
@RestController
@RequestMapping("/v2/orders")
public class ExternalOrdersSimulatorController {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public ExternalOrdersSimulatorController() {
        orders.put("ORD-1", new Order("ORD-1", 1L, 99.90, "CONFIRMED",
                List.of(new OrderItem("SKU-1", 2, 49.95))));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("errorCode", "ORDER_NOT_FOUND", "errorMessage", "Order " + orderId + " not found"));
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        String orderId = "ORD-" + sequence.incrementAndGet();
        double total = request.items().stream().mapToDouble(i -> i.unitPrice() * i.quantity()).sum();
        Order order = new Order(orderId, request.customerId(), total, "PENDING", request.items());
        orders.put(orderId, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
