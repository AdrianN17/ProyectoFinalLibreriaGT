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
 * Fábrica responsable de construir el {@link RestClient} de cada cliente Andes a partir de su
 * configuración declarativa. Combina la URL base y los timeouts de
 * {@link AndesClientProperties.ClientConfig}, instala {@link AndesClientHeaderInterceptor} para
 * encabezados estándar y logging, y conecta {@link AndesClientErrorMapper} para transformar
 * respuestas con error en excepciones del dominio Andes.
 */
public class AndesRestClientFactory {

    private final AndesClientErrorMapper errorMapper;

    /**
     * Crea la fábrica con el convertidor de errores que usarán todos los clientes construidos.
     *
     * @param errorMapper componente que transforma errores HTTP remotos en excepciones Andes
     */
    public AndesRestClientFactory(AndesClientErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    /**
     * Construye un {@link RestClient} listo para ser envuelto por {@code AndesApiClient} y
     * registrado en el {@code AndesApiClientRegistry}.
     *
     * @param clientName nombre lógico del cliente en construcción
     * @param config configuración declarada para el cliente
     * @param customizers personalizaciones adicionales aportadas por beans de Spring
     * @return cliente HTTP configurado para consumir el servicio remoto
     */
    public RestClient createClient(String clientName, AndesClientProperties.ClientConfig config,
                                    List<AndesRestClientCustomizer> customizers) {
        SimpleClientHttpRequestFactory simpleFactory = new SimpleClientHttpRequestFactory();
        simpleFactory.setConnectTimeout(config.getConnectTimeout());
        simpleFactory.setReadTimeout(config.getReadTimeout());
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
