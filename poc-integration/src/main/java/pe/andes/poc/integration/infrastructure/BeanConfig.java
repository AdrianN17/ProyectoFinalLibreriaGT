package pe.andes.poc.integration.infrastructure;

import pe.andes.poc.integration.application.port.out.CreditCardRepositoryPort;
import pe.andes.poc.integration.application.port.out.ExternalIdGeneratorPort;
import pe.andes.poc.integration.application.port.out.FraudCheckPort;
import pe.andes.poc.integration.application.service.CreditCardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    CreditCardService creditCardService(CreditCardRepositoryPort repository, FraudCheckPort fraudCheck,
                                        ExternalIdGeneratorPort idGenerator) {
        return new CreditCardService(repository, fraudCheck, idGenerator);
    }
}
