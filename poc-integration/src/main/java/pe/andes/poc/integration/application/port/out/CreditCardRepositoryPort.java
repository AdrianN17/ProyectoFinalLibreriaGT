package pe.andes.poc.integration.application.port.out;

import pe.andes.poc.integration.domain.model.CreditCard;

import java.util.Optional;

public interface CreditCardRepositoryPort {

    CreditCard save(CreditCard card);

    Optional<CreditCard> findByExternalId(String externalId);
}
