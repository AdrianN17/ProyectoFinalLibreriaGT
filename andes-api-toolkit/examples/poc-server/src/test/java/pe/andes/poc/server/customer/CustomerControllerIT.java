package pe.andes.poc.server.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import pe.andes.poc.server.generated.model.CustomerRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {

    @LocalServerPort
    int port;

    RestTestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void createAndFetchCustomerRoundTrip() {
        CustomerRequest request = new CustomerRequest().fullName("Ada Lovelace").email("ada@example.com");

        restClient.post().uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("\"success\":true").contains("ada@example.com"));
    }

    @Test
    void notFoundCustomerReturnsStandardErrorEnvelope() {
        restClient.get().uri("/api/v1/customers/999999")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("\"success\":false").contains("NOT_FOUND"));
    }

    @Test
    void invalidCustomerReturnsValidationError() {
        CustomerRequest invalid = new CustomerRequest().fullName("ab").email("not-an-email");

        restClient.post().uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(invalid)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
