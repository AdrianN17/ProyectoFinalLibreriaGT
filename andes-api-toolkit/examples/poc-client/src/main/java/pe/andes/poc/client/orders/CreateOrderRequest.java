package pe.andes.poc.client.orders;

import java.util.List;

public record CreateOrderRequest(Long customerId, List<OrderItem> items) {
}
