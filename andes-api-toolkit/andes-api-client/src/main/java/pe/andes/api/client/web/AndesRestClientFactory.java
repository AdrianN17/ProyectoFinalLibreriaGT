package pe.andes.api.client.web;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import pe.andes.api.client.config.AndesClientProperties;
import pe.andes.api.client.error.AndesClientErrorMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Builds a configured {@link RestClient} for a named client configuration: base URL,
 * connect/read timeouts, standard headers (correlation id, request id, content type)
 * and centralized HTTP-error-to-exception mapping.
 */
public class AndesRestClientFactory {

    private final AndesClientErrorMapper errorMapper;

    public AndesRestClientFactory(AndesClientErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    public RestClient createClient(String clientName, AndesClientProperties.ClientConfig config,
                                    List<AndesRestClientCustomizer> customizers) {
        SimpleClientHttpRequestFactory simpleFactory = new SimpleClientHttpRequestFactory();
        simpleFactory.setConnectTimeout(config.getConnectTimeout());
        simpleFactory.setReadTimeout(config.getReadTimeout());
        // Buffer the response so the error status handler below can read the body without
        // consuming the single-use stream that the underlying HttpURLConnection exposes.
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(simpleFactory);

        RestClient.Builder builder = RestClient.builder()
                .baseUrl(config.getBaseUrl())
                .requestFactory(requestFactory)
                .requestInterceptor(new AndesClientHeaderInterceptor(config))
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    throw errorMapper.map(request.getURI().toString(), response.getStatusCode().value(), body);
                });

        for (AndesRestClientCustomizer customizer : customizers) {
            customizer.customize(clientName, builder);
        }

        return builder.build();
    }
}
