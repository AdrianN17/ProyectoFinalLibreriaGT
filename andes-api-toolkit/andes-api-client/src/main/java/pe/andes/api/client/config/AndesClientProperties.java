package pe.andes.api.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Propiedades raíz del módulo {@code andes-api-client}. Spring Boot enlaza esta clase con el
 * prefijo {@code andes.api.client} y la autoconfiguración la usa para crear un
 * {@link pe.andes.api.client.AndesApiClient} por cada entrada definida en {@link #clients}.
 *
 * <pre>{@code
 * andes:
 *   api:
 *     client:
 *       clients:
 *         customer:
 *           base-url: ${CUSTOMER_API_URL}
 *           connect-timeout: 2s
 *           read-timeout: 5s
 * }</pre>
 */
@ConfigurationProperties(prefix = "andes.api.client")
public class AndesClientProperties {

    /**
     * Conjunto de clientes declarados en configuración, indexados por su nombre lógico.
     */
    private Map<String, ClientConfig> clients = new LinkedHashMap<>();

    /**
     * Devuelve la definición de todos los clientes configurados.
     *
     * @return mapa de configuraciones por nombre lógico
     */
    public Map<String, ClientConfig> getClients() {
        return clients;
    }

    /**
     * Reemplaza el mapa completo de configuraciones de clientes.
     *
     * @param clients configuraciones de clientes indexadas por nombre lógico
     */
    public void setClients(Map<String, ClientConfig> clients) {
        this.clients = clients;
    }

    /**
     * Configuración individual de un cliente remoto. Esta información alimenta al
     * {@link pe.andes.api.client.web.AndesRestClientFactory}, que construye el
     * {@link org.springframework.web.client.RestClient} con URL base, timeouts e interceptores.
     */
    public static class ClientConfig {

        /**
         * URL base del servicio remoto al que apuntará el cliente.
         */
        private String baseUrl;

        /**
         * Tiempo máximo permitido para establecer la conexión HTTP.
         */
        private Duration connectTimeout = Duration.ofSeconds(2);

        /**
         * Tiempo máximo de espera para recibir la respuesta HTTP.
         */
        private Duration readTimeout = Duration.ofSeconds(5);

        /**
         * Indica si el interceptor debe propagar o generar el encabezado de correlación.
         */
        private boolean correlationIdEnabled = true;

        /**
         * Indica si el interceptor debe generar el encabezado de identificador de petición.
         */
        private boolean requestIdEnabled = true;

        /**
         * Encabezados fijos que se añaden a cada solicitud cuando todavía no existen.
         */
        private Map<String, String> defaultHeaders = new LinkedHashMap<>();

        /**
         * Devuelve la URL base del servicio remoto.
         *
         * @return URL base configurada
         */
        public String getBaseUrl() {
            return baseUrl;
        }

        /**
         * Define la URL base del servicio remoto.
         *
         * @param baseUrl URL base del cliente
         */
        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        /**
         * Devuelve el timeout de conexión HTTP.
         *
         * @return duración máxima para establecer conexión
         */
        public Duration getConnectTimeout() {
            return connectTimeout;
        }

        /**
         * Define el timeout de conexión HTTP.
         *
         * @param connectTimeout duración máxima para establecer conexión
         */
        public void setConnectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        /**
         * Devuelve el timeout de lectura de la respuesta HTTP.
         *
         * @return duración máxima de espera de respuesta
         */
        public Duration getReadTimeout() {
            return readTimeout;
        }

        /**
         * Define el timeout de lectura de la respuesta HTTP.
         *
         * @param readTimeout duración máxima de espera de respuesta
         */
        public void setReadTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
        }

        /**
         * Indica si está habilitada la propagación o generación de correlation id.
         *
         * @return {@code true} si el interceptor debe manejar correlation id
         */
        public boolean isCorrelationIdEnabled() {
            return correlationIdEnabled;
        }

        /**
         * Habilita o deshabilita la propagación o generación de correlation id.
         *
         * @param correlationIdEnabled valor que controla el encabezado de correlación
         */
        public void setCorrelationIdEnabled(boolean correlationIdEnabled) {
            this.correlationIdEnabled = correlationIdEnabled;
        }

        /**
         * Indica si está habilitada la generación de request id.
         *
         * @return {@code true} si el interceptor debe añadir request id
         */
        public boolean isRequestIdEnabled() {
            return requestIdEnabled;
        }

        /**
         * Habilita o deshabilita la generación de request id.
         *
         * @param requestIdEnabled valor que controla el encabezado de petición
         */
        public void setRequestIdEnabled(boolean requestIdEnabled) {
            this.requestIdEnabled = requestIdEnabled;
        }

        /**
         * Devuelve los encabezados por defecto del cliente.
         *
         * @return mapa de encabezados que se agregan a cada solicitud
         */
        public Map<String, String> getDefaultHeaders() {
            return defaultHeaders;
        }

        /**
         * Reemplaza el mapa de encabezados por defecto del cliente.
         *
         * @param defaultHeaders encabezados que se agregarán cuando no existan en la solicitud
         */
        public void setDefaultHeaders(Map<String, String> defaultHeaders) {
            this.defaultHeaders = defaultHeaders;
        }
    }
}
