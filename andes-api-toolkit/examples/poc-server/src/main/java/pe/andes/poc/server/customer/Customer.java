package pe.andes.poc.server.customer;

import java.time.Instant;

public record Customer(Long id, String fullName, String email, Instant createdAt) {
}
