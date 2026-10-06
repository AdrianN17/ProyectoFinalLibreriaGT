package pe.andes.poc.integration.infrastructure.in.rest;

import pe.andes.poc.integration.generated.fraud.model.FraudCheckRequest;
import pe.andes.poc.integration.generated.fraud.model.FraudCheckResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Local stand-in for the external Fraud Check API so the PoC runs standalone.
 * Disable with poc.fraud-simulator.enabled=false and point andes.api.client.clients.fraud.base-url at the real one.
 * Cards ending in 0000 are rejected.
 */
@RestController
@RequestMapping("/api/v1/fraud-check")
@ConditionalOnProperty(name = "poc.fraud-simulator.enabled", havingValue = "true", matchIfMissing = true)
public class FraudCheckSimulatorController {

    @PostMapping
    public FraudCheckResponse check(@RequestBody FraudCheckRequest request) {
        boolean rejected = request.getCardNumber().endsWith("0000");
        return new FraudCheckResponse()
                .approved(!rejected)
                .score(rejected ? 95 : 10)
                .reason(rejected ? "Card flagged as high risk" : "OK");
    }
}
