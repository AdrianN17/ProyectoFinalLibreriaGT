package pe.andes.api.client.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import pe.andes.api.client.config.AndesClientProperties;
import pe.andes.api.common.http.AndesApiConstants;
import pe.andes.api.common.http.AndesContentTypes;
import pe.andes.api.common.http.AndesHeaders;
import pe.andes.api.common.util.HeaderUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Interceptor HTTP que completa encabezados estándar y registra cada invocación saliente de un
 * cliente Andes. El {@link pe.andes.api.client.web.AndesRestClientFactory} lo instala en el
 * {@link org.springframework.web.client.RestClient} de cada cliente para propagar correlation id,
 * generar request id, aplicar {@code Content-Type} y añadir encabezados por defecto definidos en
 * {@link AndesClientProperties.ClientConfig}.
 */
public class AndesClientHeaderInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AndesClientHeaderInterceptor.class);

    private final AndesClientProperties.ClientConfig config;

    /**
     * Crea el interceptor usando la configuración del cliente al que estará asociado.
     *
     * @param config configuración del cliente que define qué encabezados automáticos aplicar
     */
    public AndesClientHeaderInterceptor(AndesClientProperties.ClientConfig config) {
        this.config = config;
    }

    /**
     * Intercepta la solicitud saliente, completa los encabezados faltantes y registra una línea de
     * log antes y después de ejecutar la llamada remota.
     *
     * @param request solicitud HTTP que se enviará
     * @param body cuerpo serializado de la solicitud
     * @param execution cadena responsable de continuar la ejecución
     * @return respuesta HTTP del servicio remoto
     * @throws IOException si ocurre un problema de entrada o salida durante la ejecución
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();

        if (config.isCorrelationIdEnabled() && !headers.containsHeader(AndesHeaders.CORRELATION_ID)) {
            String correlationId = MDC.get(AndesApiConstants.MDC_CORRELATION_ID);
            headers.add(AndesHeaders.CORRELATION_ID,
                    HeaderUtils.isValidCorrelationId(correlationId) ? correlationId : HeaderUtils.generateCorrelationId());
        }
        if (config.isRequestIdEnabled() && !headers.containsHeader(AndesHeaders.REQUEST_ID)) {
            headers.add(AndesHeaders.REQUEST_ID, HeaderUtils.generateRequestId());
        }
        if (!headers.containsHeader(AndesHeaders.CONTENT_TYPE)) {
            headers.add(AndesHeaders.CONTENT_TYPE, AndesContentTypes.APPLICATION_JSON);
        }
        for (Map.Entry<String, String> header : config.getDefaultHeaders().entrySet()) {
            if (!headers.containsHeader(header.getKey())) {
                headers.add(header.getKey(), header.getValue());
            }
        }

        long startedAt = System.currentTimeMillis();
        log.info(">> {} {}", request.getMethod(), request.getURI());
        ClientHttpResponse response = execution.execute(request, body);
        log.info("<< {} {} -> {} ({} ms)", request.getMethod(), request.getURI(),
                response.getStatusCode().value(), System.currentTimeMillis() - startedAt);
        return response;
    }
}
