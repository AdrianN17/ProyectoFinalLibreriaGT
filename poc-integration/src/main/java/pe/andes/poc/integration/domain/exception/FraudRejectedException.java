package pe.andes.poc.integration.domain.exception;

public class FraudRejectedException extends RuntimeException {

    public FraudRejectedException(String reason, int score) {
        super("Credit card rejected by fraud check (score " + score + "): " + reason);
    }
}
