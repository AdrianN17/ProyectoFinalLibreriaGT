package pe.andes.poc.server.customer;

import org.springframework.stereotype.Service;
import pe.andes.api.common.exception.AndesConflictException;
import pe.andes.api.common.exception.AndesNotFoundException;
import pe.andes.api.common.model.PageResponse;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory Customer repository/service, kept intentionally simple: the goal of this
 * PoC is to demonstrate andes-api-server, not a persistence layer.
 */
@Service
public class CustomerService {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    public PageResponse<Customer> list(int page, int size) {
        List<Customer> all = customers.values().stream()
                .sorted((a, b) -> Long.compare(a.id(), b.id()))
                .toList();
        int from = Math.min(page * size, all.size());
        int to = Math.min(from + size, all.size());
        return PageResponse.of(all.subList(from, to), page, size, all.size());
    }

    public Customer getById(Long id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new AndesNotFoundException("Customer " + id + " not found");
        }
        return customer;
    }

    public Customer create(CustomerRequest request) {
        boolean emailTaken = customers.values().stream()
                .anyMatch(c -> c.email().equalsIgnoreCase(request.email()));
        if (emailTaken) {
            throw new AndesConflictException("Email already registered: " + request.email());
        }
        long id = idSequence.incrementAndGet();
        Customer customer = new Customer(id, request.fullName(), request.email(), Instant.now());
        customers.put(id, customer);
        return customer;
    }

    public void delete(Long id) {
        if (customers.remove(id) == null) {
            throw new AndesNotFoundException("Customer " + id + " not found");
        }
    }
}
