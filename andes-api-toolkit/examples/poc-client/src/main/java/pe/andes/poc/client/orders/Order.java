package pe.andes.poc.client.orders;

import java.util.List;

/**
 * Mirrors the {@code Order} schema of {@code contracts/openapi-client-a.yaml}.
 */
public record Order(String orderId, Long customerId, double totalAmount, String status, List<OrderItem> items) {
}
