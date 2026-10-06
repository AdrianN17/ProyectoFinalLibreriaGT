package pe.andes.poc.integration.application.port.in;

import pe.andes.poc.integration.domain.model.CreditCard;

public interface CreateCreditCardUseCase {

    CreditCard create(Command command);

    record Command(String holderName, String cardNumber, String documentNumber) {
    }
}
