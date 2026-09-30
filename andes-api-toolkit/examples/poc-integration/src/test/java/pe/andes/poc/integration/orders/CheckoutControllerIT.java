package pe.andes.poc.integration.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CheckoutControllerIT {

    private static final String BASE_URL = "http://localhost:8083";

    RestTestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestTestClient.bindToServer().baseUrl(BASE_URL).build();
    }

    @Test
    void checkoutFlowsThroughOwnApiBusinessLogicAndClientLibrary() {
        Map<String, Object> request = Map.of(
                "customerId", 1,
                "items", java.util.List.of(Map.of("sku", "SKU-1", "quantity", 2, "unitPrice", 10.0)));

        restClient.post().uri("/api/v1/checkouts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("\"success\":true").contains("PENDING"));
    }

    @Test
    void notFoundOrderIsMappedThroughClientAndServerErrorHandling() {
        restClient.get().uri("/api/v1/checkouts/UNKNOWN")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("\"success\":false"));
    }
}
