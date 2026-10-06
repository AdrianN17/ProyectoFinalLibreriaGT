package pe.andes.poc.integration.infrastructure.out.id;

import pe.andes.poc.integration.application.port.out.ExternalIdGeneratorPort;
import org.springframework.stereotype.Component;
import pe.andes.lib.id.autoconfigure.IdGeneratorService;

/** Adapter over andes-id-generator. */
@Component
public class UlidExternalIdGenerator implements ExternalIdGeneratorPort {

    private final IdGeneratorService idGenerator;

    public UlidExternalIdGenerator(IdGeneratorService idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public String next() {
        return idGenerator.newId("CC");
    }
}
