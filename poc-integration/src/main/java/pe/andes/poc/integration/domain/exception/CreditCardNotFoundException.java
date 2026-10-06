package pe.andes.poc.integration.domain.exception;

public class CreditCardNotFoundException extends RuntimeException {

    public CreditCardNotFoundException(String externalId) {
        super("Credit card " + externalId + " not found");
    }
}
