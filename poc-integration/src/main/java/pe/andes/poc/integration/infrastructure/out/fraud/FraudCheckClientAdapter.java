package pe.andes.poc.integration.infrastructure.out.fraud;

import pe.andes.poc.integration.application.port.out.FraudCheckPort;
import pe.andes.poc.integration.domain.model.FraudVerdict;
import pe.andes.poc.integration.generated.fraud.model.FraudCheckRequest;
import pe.andes.poc.integration.generated.fraud.model.FraudCheckResponse;
import org.springframework.stereotype.Component;
import pe.andes.api.client.AndesApiClient;
import pe.andes.api.client.AndesApiClientRegistry;

/** Outbound adapter: calls the external Fraud Check API (openapi-fraudcheck.yaml) via andes-api-client. */
@Component
public class FraudCheckClientAdapter implements FraudCheckPort {

    private final AndesApiClient client;

    public FraudCheckClientAdapter(AndesApiClientRegistry registry) {
        this.client = registry.get("fraud");
    }

    @Override
    public FraudVerdict check(String cardNumber, String documentNumber) {
        FraudCheckRequest request = new FraudCheckRequest().cardNumber(cardNumber).documentNumber(documentNumber);
        FraudCheckResponse response = client.post("/api/v1/fraud-check", request, FraudCheckResponse.class);
        return new FraudVerdict(Boolean.TRUE.equals(response.getApproved()), response.getScore(), response.getReason());
    }
}
