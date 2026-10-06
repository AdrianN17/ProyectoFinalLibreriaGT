package pe.andes.poc.integration.infrastructure.in.rest;

import pe.andes.poc.integration.application.port.in.CreateCreditCardUseCase;
import pe.andes.poc.integration.application.port.in.GetCreditCardUseCase;
import pe.andes.poc.integration.domain.exception.CreditCardNotFoundException;
import pe.andes.poc.integration.domain.exception.FraudRejectedException;
import pe.andes.poc.integration.domain.model.CreditCard;
import pe.andes.poc.integration.generated.api.CreditCardsApiDelegate;
import pe.andes.poc.integration.generated.model.CreateCreditCardRequest;
import pe.andes.poc.integration.generated.model.CreditCardEnvelope;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pe.andes.api.common.exception.AndesNotFoundException;
import pe.andes.api.common.exception.AndesValidationException;
import pe.andes.api.common.http.AndesApiConstants;
import pe.andes.api.common.model.ApiMetadata;

/**
 * Inbound REST adapter: implements the delegate generated from openapi-creditcard.yaml, calls the
 * use cases and translates domain exceptions into Andes exceptions (handled by andes-api-server).
 */
@Component
public class CreditCardApiDelegateImpl implements CreditCardsApiDelegate {

    private final CreateCreditCardUseCase createUseCase;
    private final GetCreditCardUseCase getUseCase;

    public CreditCardApiDelegateImpl(CreateCreditCardUseCase createUseCase, GetCreditCardUseCase getUseCase) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
    }

    @Override
    public ResponseEntity<CreditCardEnvelope> createCreditCard(CreateCreditCardRequest request) {
        try {
            CreditCard card = createUseCase.create(new CreateCreditCardUseCase.Command(
                    request.getHolderName(), request.getCardNumber(), request.getDocumentNumber()));
            return ResponseEntity.status(HttpStatus.CREATED).body(envelope(card));
        } catch (FraudRejectedException e) {
            throw new AndesValidationException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<CreditCardEnvelope> getCreditCardByExternalId(String externalId) {
        try {
            return ResponseEntity.ok(envelope(getUseCase.getByExternalId(externalId)));
        } catch (CreditCardNotFoundException e) {
            throw new AndesNotFoundException(e.getMessage());
        }
    }

    private CreditCardEnvelope envelope(CreditCard card) {
        var data = new pe.andes.poc.integration.generated.model.CreditCard()
                .id(card.id())
                .externalId(card.externalId())
                .holderName(card.holderName())
                .maskedCardNumber(card.maskedCardNumber())
                .maskedDocumentNumber(card.maskedDocumentNumber())
                .status(pe.andes.poc.integration.generated.model.CreditCard.StatusEnum.valueOf(card.status().name()));
        String correlationId = MDC.get(AndesApiConstants.MDC_CORRELATION_ID);
        ApiMetadata metadata = ApiMetadata.builder()
                .traceId(correlationId)
                .correlationId(correlationId)
                .requestId(MDC.get(AndesApiConstants.MDC_REQUEST_ID))
                .build();
        return new CreditCardEnvelope().success(true).data(data).metadata(metadata);
    }
}
