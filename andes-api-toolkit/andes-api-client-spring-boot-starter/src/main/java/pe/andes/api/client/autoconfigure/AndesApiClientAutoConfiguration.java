package pe.andes.api.client.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import pe.andes.api.client.AndesApiClient;
import pe.andes.api.client.AndesApiClientRegistry;
import pe.andes.api.client.config.AndesClientProperties;
import pe.andes.api.client.error.AndesClientErrorMapper;
import pe.andes.api.client.web.AndesRestClientCustomizer;
import pe.andes.api.client.web.AndesRestClientFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Autoconfiguración de Spring Boot para el ecosistema de clientes Andes. Habilita el binding de
 * {@link AndesClientProperties}, crea los beans de infraestructura necesarios y registra un
 * {@link AndesApiClient} por cada entrada declarada en {@code andes.api.client.clients.*}. De
 * esta manera, la relación entre configuración, {@link RestClient}, interceptores, mapeo de
 * errores y registro de clientes queda resuelta automáticamente para la aplicación consumidora.
 */
@AutoConfiguration
@EnableConfigurationProperties(AndesClientProperties.class)
public class AndesApiClientAutoConfiguration {

    /**
     * Expone el mapper de errores por defecto cuando la aplicación no define uno propio.
     *
     * @return mapper estándar para traducir errores HTTP remotos
     */
    @Bean
    @ConditionalOnMissingBean
    public AndesClientErrorMapper andesClientErrorMapper() {
        return new AndesClientErrorMapper();
    }

    /**
     * Expone la fábrica que construye {@link RestClient} configurados para cada cliente Andes.
     *
     * @param andesClientErrorMapper mapper de errores que se conectará al manejo de estados HTTP
     * @return fábrica de clientes HTTP Andes
     */
    @Bean
    @ConditionalOnMissingBean
    public AndesRestClientFactory andesRestClientFactory(AndesClientErrorMapper andesClientErrorMapper) {
        return new AndesRestClientFactory(andesClientErrorMapper);
    }

    /**
     * Registra el contenedor con todos los clientes Andes construidos a partir de la
     * configuración. Cada entrada usa la fábrica para obtener su {@link RestClient}, el cual ya
     * incluye interceptores estándar y cualquier {@link AndesRestClientCustomizer} declarado en el
     * contexto de Spring.
     *
     * @param properties propiedades enlazadas desde la configuración externa
     * @param factory fábrica responsable de construir cada {@link RestClient}
     * @param customizers personalizaciones adicionales aplicables a todos los clientes
     * @return registro inmutable de clientes Andes disponibles en la aplicación
     */
    @Bean
    @ConditionalOnMissingBean
    public AndesApiClientRegistry andesApiClientRegistry(AndesClientProperties properties,
                                                             AndesRestClientFactory factory,
                                                             List<AndesRestClientCustomizer> customizers) {
        Map<String, AndesApiClient> clients = new LinkedHashMap<>();
        properties.getClients().forEach((name, config) -> {
            RestClient restClient = factory.createClient(name, config, customizers);
            clients.put(name, new AndesApiClient(name, restClient));
        });
        return new AndesApiClientRegistry(clients);
    }
}
