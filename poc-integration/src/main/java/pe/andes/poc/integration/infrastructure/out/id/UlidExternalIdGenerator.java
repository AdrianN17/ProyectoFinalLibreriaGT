package pe.andes.poc.integration.infrastructure.out.id;

import pe.andes.poc.integration.application.port.out.ExternalIdGeneratorPort;
import org.springframework.stereotype.Component;
import pe.andes.lib.id.UlidGenerator;

/** Adapter over andes-id-generator. */
@Component
public class UlidExternalIdGenerator implements ExternalIdGeneratorPort {

    @Override
    public String next() {
        return UlidGenerator.withPrefix("CC");
    }
}
