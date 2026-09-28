package pe.andes.poc.client.orders;

import org.springframework.stereotype.Service;
import pe.andes.api.client.AndesApiClient;
import pe.andes.api.client.AndesApiClientRegistry;

/**
 * Demonstrates consuming the external Orders API through the {@code orders} named client
 * configured in {@code application.yml} (headers, timeouts, serialization and error mapping
 * are all handled by andes-api-client).
 */
@Service
public class OrderClientService {

    private final AndesApiClient ordersClient;

    public OrderClientService(AndesApiClientRegistry registry) {
        this.ordersClient = registry.get("orders");
    }

    public Order getOrder(String orderId) {
        return ordersClient.get("/v2/orders/" + orderId, Order.class);
    }

    public Order createOrder(CreateOrderRequest request) {
        return ordersClient.post("/v2/orders", request, Order.class);
    }
}
