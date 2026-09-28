package pe.andes.poc.integration.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CheckoutRequest(
        @NotNull Long customerId,
        @NotEmpty @Valid List<OrderItem> items) {
}
