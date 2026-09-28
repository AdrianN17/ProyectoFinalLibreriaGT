package pe.andes.poc.integration.orders;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The "Own API" step of the checkout flow. Request validation, response wrapping and error
 * handling are all provided automatically by andes-api-server-spring-boot-starter; this
 * controller only implements business logic delegation.
 */
@RestController
@RequestMapping("/api/v1/checkouts")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request);
    }

    @GetMapping("/{orderId}")
    public Order getCheckout(@PathVariable String orderId) {
        return checkoutService.findOrder(orderId);
    }
}
