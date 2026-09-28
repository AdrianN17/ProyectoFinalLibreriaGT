package pe.andes.poc.server.customer;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.andes.api.common.model.PageResponse;

/**
 * Implements the endpoints declared in {@code contracts/openapi-server.yaml}. Return
 * values are plain domain/DTO objects: {@code AndesResponseBodyAdvice} (registered by
 * andes-api-server-spring-boot-starter) automatically wraps them into the standard
 * {@code ApiResponse} envelope, and {@code GlobalExceptionHandler} automatically maps
 * thrown {@code AndesApiException}s (and validation errors) to the matching HTTP status.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public PageResponse<Customer> list(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return customerService.list(page, size);
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@Valid @RequestBody CustomerRequest request) {
        return customerService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}
