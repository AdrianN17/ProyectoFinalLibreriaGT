package pe.andes.poc.integration.orders;

import org.springframework.stereotype.Service;

/**
 * The "Business logic" step of the checkout flow, sitting between the own exposed API
 * ({@link CheckoutController}) and the remote API call ({@link OrdersRemoteService}).
 */
@Service
public class CheckoutService {

    private final OrdersRemoteService ordersRemoteService;

    public CheckoutService(OrdersRemoteService ordersRemoteService) {
        this.ordersRemoteService = ordersRemoteService;
    }

    public Order checkout(CheckoutRequest request) {
        // Business rules would be applied here (pricing, fraud checks, etc.) before
        // delegating the actual order placement to the remote Orders API.
        return ordersRemoteService.placeOrder(request);
    }

    public Order findOrder(String orderId) {
        return ordersRemoteService.getOrder(orderId);
    }
}
