package pe.andes.poc.integration.application.port.out;

import pe.andes.poc.integration.domain.model.FraudVerdict;

public interface FraudCheckPort {

    FraudVerdict check(String cardNumber, String documentNumber);
}
