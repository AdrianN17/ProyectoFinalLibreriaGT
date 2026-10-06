package pe.andes.poc.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CreditCardControllerIT {

    RestTestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    private Map<String, String> body(String card) {
        return Map.of("holderName", "Ada Lovelace", "cardNumber", card, "documentNumber", "12345678");
    }

    @Test
    void createsCardThroughFraudCheckAndMasksSensitiveData() {
        restClient.post().uri("/api/v1/credit-cards")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body("4111111111111111"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(String.class)
                .value(b -> assertThat(b).contains("\"success\":true").contains("APPROVED")
                        .doesNotContain("4111111111111111"));
    }

    @Test
    void fraudRejectionIs422() {
        restClient.post().uri("/api/v1/credit-cards")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body("4111111111110000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(String.class)
                .value(b -> assertThat(b).contains("\"success\":false"));
    }

    @Test
    void unknownCardIs404() {
        restClient.get().uri("/api/v1/credit-cards/UNKNOWN")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }
}
