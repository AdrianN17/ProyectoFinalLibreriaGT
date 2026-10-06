package pe.andes.poc.integration.domain.model;

/** Aggregate: only masked card/document data is ever kept. */
public record CreditCard(
        Long id,
        String externalId,
        String holderName,
        String maskedCardNumber,
        String maskedDocumentNumber,
        CardStatus status) {

    public CreditCard withId(Long newId) {
        return new CreditCard(newId, externalId, holderName, maskedCardNumber, maskedDocumentNumber, status);
    }
}
