package pe.andes.poc.integration.application.service;

import pe.andes.poc.integration.application.port.in.CreateCreditCardUseCase;
import pe.andes.poc.integration.application.port.in.GetCreditCardUseCase;
import pe.andes.poc.integration.application.port.out.CreditCardRepositoryPort;
import pe.andes.poc.integration.application.port.out.ExternalIdGeneratorPort;
import pe.andes.poc.integration.application.port.out.FraudCheckPort;
import pe.andes.poc.integration.domain.exception.CreditCardNotFoundException;
import pe.andes.poc.integration.domain.exception.FraudRejectedException;
import pe.andes.poc.integration.domain.model.CardStatus;
import pe.andes.poc.integration.domain.model.CreditCard;
import pe.andes.poc.integration.domain.model.FraudVerdict;
import pe.andes.lib.text.MaskUtils;

public class CreditCardService implements CreateCreditCardUseCase, GetCreditCardUseCase {

    private final CreditCardRepositoryPort repository;
    private final FraudCheckPort fraudCheck;
    private final ExternalIdGeneratorPort idGenerator;

    public CreditCardService(CreditCardRepositoryPort repository, FraudCheckPort fraudCheck,
                             ExternalIdGeneratorPort idGenerator) {
        this.repository = repository;
        this.fraudCheck = fraudCheck;
        this.idGenerator = idGenerator;
    }

    @Override
    public CreditCard create(Command command) {
        FraudVerdict verdict = fraudCheck.check(command.cardNumber(), command.documentNumber());
        if (!verdict.approved()) {
            throw new FraudRejectedException(verdict.reason(), verdict.score());
        }
        CreditCard card = new CreditCard(
                null,
                idGenerator.next(),
                command.holderName(),
                MaskUtils.maskDigits(command.cardNumber(), 4),
                MaskUtils.maskDigits(command.documentNumber(), 3),
                CardStatus.APPROVED);
        return repository.save(card);
    }

    @Override
    public CreditCard getByExternalId(String externalId) {
        return repository.findByExternalId(externalId)
                .orElseThrow(() -> new CreditCardNotFoundException(externalId));
    }
}
