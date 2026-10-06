package pe.andes.poc.integration.application.port.in;

import pe.andes.poc.integration.domain.model.CreditCard;

public interface GetCreditCardUseCase {

    CreditCard getByExternalId(String externalId);
}
