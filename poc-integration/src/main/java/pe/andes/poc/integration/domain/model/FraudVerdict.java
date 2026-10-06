package pe.andes.poc.integration.domain.model;

public record FraudVerdict(boolean approved, int score, String reason) {
}
