package pe.andes.poc.integration.infrastructure.out.persistence;

import pe.andes.poc.integration.application.port.out.CreditCardRepositoryPort;
import pe.andes.poc.integration.domain.model.CreditCard;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCreditCardRepository implements CreditCardRepositoryPort {

    private final Map<String, CreditCard> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public CreditCard save(CreditCard card) {
        CreditCard saved = card.id() == null ? card.withId(sequence.incrementAndGet()) : card;
        store.put(saved.externalId(), saved);
        return saved;
    }

    @Override
    public Optional<CreditCard> findByExternalId(String externalId) {
        return Optional.ofNullable(store.get(externalId));
    }
}
