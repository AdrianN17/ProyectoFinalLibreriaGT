package pe.andes.poc.integration.orders;

import java.util.List;

public record Order(String orderId, Long customerId, double totalAmount, String status, List<OrderItem> items) {
}
