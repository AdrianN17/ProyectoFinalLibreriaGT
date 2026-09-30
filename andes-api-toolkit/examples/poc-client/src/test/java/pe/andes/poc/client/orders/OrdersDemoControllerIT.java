package pe.andes.poc.client.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrdersDemoControllerIT {

    private static final String BASE_URL = "http://localhost:8082";

    RestTestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestTestClient.bindToServer().baseUrl(BASE_URL).build();
    }

    @Test
    void fetchesExistingOrderThroughAndesApiClient() {
        restClient.get().uri("/api/v1/orders-demo/ORD-1")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("\"success\":true").contains("ORD-1"));
    }

    @Test
    void missingOrderIsMappedToNotFound() {
        restClient.get().uri("/api/v1/orders-demo/DOES-NOT-EXIST")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("\"success\":false"));
    }
}
