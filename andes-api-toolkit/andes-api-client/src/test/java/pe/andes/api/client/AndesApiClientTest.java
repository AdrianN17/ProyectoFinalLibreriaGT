package pe.andes.api.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class AndesApiClientTest {

    private WireMockServer server;
    private AndesApiClient client;

    @BeforeEach
    void setUp() {
        server = new WireMockServer(0);
        server.start();
        client = new AndesApiClient("t", RestClient.builder().requestFactory(new org.springframework.http.client.JdkClientHttpRequestFactory(
                java.net.http.HttpClient.newBuilder().version(java.net.http.HttpClient.Version.HTTP_1_1).build())).baseUrl("http://localhost:" + server.port()).build());
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    private static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder json(String body) {
        return aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body);
    }

    @Test
    void getWithClassAndTypeReference() {
        server.stubFor(get(urlEqualTo("/a")).willReturn(json("{\"k\":\"v\"}")));
        server.stubFor(get(urlEqualTo("/l")).willReturn(json("[\"x\",\"y\"]")));

        assertEquals("v", client.get("/a", Map.class).get("k"));
        List<String> list = client.get("/l", new ParameterizedTypeReference<List<String>>() {});
        assertEquals(List.of("x", "y"), list);
    }

    @Test
    void postPutPatchSendBodyAndReadResponse() {
        server.stubFor(post(urlEqualTo("/p")).willReturn(json("{\"m\":\"post\"}")));
        server.stubFor(put(urlEqualTo("/p")).willReturn(json("{\"m\":\"put\"}")));
        server.stubFor(patch(urlEqualTo("/p")).willReturn(json("{\"m\":\"patch\"}")));
        server.stubFor(post(urlEqualTo("/t")).willReturn(json("[\"z\"]")));

        assertEquals("post", client.post("/p", Map.of("a", 1), Map.class).get("m"));
        assertEquals("put", client.put("/p", Map.of("a", 1), Map.class).get("m"));
        assertEquals("patch", client.patch("/p", Map.of("a", 1), Map.class).get("m"));
        assertEquals(List.of("z"), client.post("/t", Map.of(), new ParameterizedTypeReference<List<String>>() {}));
        server.verify(postRequestedFor(urlEqualTo("/p")).withRequestBody(equalToJson("{\"a\":1}")));
    }

    @Test
    void deleteIssuesDeleteRequest() {
        server.stubFor(delete(urlEqualTo("/d")).willReturn(aResponse().withStatus(204)));
        client.delete("/d");
        server.verify(deleteRequestedFor(urlEqualTo("/d")));
    }
}
