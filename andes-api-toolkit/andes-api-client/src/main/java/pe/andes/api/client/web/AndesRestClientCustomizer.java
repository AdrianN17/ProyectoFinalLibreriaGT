package pe.andes.api.client.web;

import org.springframework.web.client.RestClient;

/**
 * Punto de extensión para ajustar el {@link RestClient.Builder} de un cliente Andes antes de su
 * creación final. La autoconfiguración recopila todas las implementaciones registradas como beans
 * de Spring y se las entrega a {@link AndesRestClientFactory} para complementar la configuración
 * base con autenticación, interceptores adicionales o convertidores específicos.
 */
@FunctionalInterface
public interface AndesRestClientCustomizer {

    /**
     * Personaliza el constructor del cliente asociado al nombre lógico recibido.
     *
     * @param clientName nombre lógico del cliente que se está construyendo
     * @param builder constructor de {@link RestClient} que podrá modificarse antes del
     *                {@code build()}
     */
    void customize(String clientName, RestClient.Builder builder);
}
