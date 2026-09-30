package pe.andes.api.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

/**
 * Fachada tipada y ligera sobre un {@link RestClient} ya configurado para un cliente lógico
 * de Andes. La autoconfiguración crea instancias de esta clase a partir de
 * {@link pe.andes.api.client.config.AndesClientProperties}, mientras que
 * {@link AndesApiClientRegistry} permite resolverlas por nombre para que el código de negocio
 * consuma APIs remotas sin repetir configuración de transporte, encabezados o manejo de errores.
 */
public class AndesApiClient {

    private final String name;
    private final RestClient restClient;

    /**
     * Crea un cliente Andes asociado a un nombre lógico y a un {@link RestClient} ya preparado.
     *
     * @param name nombre lógico con el que el cliente se registra en el
     *             {@link AndesApiClientRegistry}
     * @param restClient cliente HTTP subyacente configurado por la fábrica para ejecutar las
     *                   llamadas remotas
     */
    public AndesApiClient(String name, RestClient restClient) {
        this.name = name;
        this.restClient = restClient;
    }

    /**
     * Devuelve el nombre lógico con el que este cliente fue registrado.
     *
     * @return nombre del cliente configurado
     */
    public String getName() {
        return name;
    }

    /**
     * Expone el {@link RestClient} subyacente para escenarios avanzados donde se necesita usar la
     * API nativa de Spring, por ejemplo multipart, streaming o configuraciones particulares por
     * invocación.
     *
     * @return instancia de {@link RestClient} utilizada por este cliente
     */
    public RestClient raw() {
        return restClient;
    }

    /**
     * Ejecuta una petición HTTP GET y deserializa la respuesta al tipo indicado.
     *
     * @param uri ruta o URI relativa al {@code baseUrl} configurado
     * @param responseType tipo concreto esperado en la respuesta
     * @param <T> tipo del cuerpo de respuesta
     * @return cuerpo de respuesta deserializado
     */
    public <T> T get(String uri, Class<T> responseType) {
        return restClient.get().uri(uri).retrieve().body(responseType);
    }

    /**
     * Ejecuta una petición HTTP GET y deserializa la respuesta usando un tipo parametrizado.
     *
     * @param uri ruta o URI relativa al {@code baseUrl} configurado
     * @param responseType definición del tipo parametrizado esperado en la respuesta
     * @param <T> tipo del cuerpo de respuesta
     * @return cuerpo de respuesta deserializado
     */
    public <T> T get(String uri, ParameterizedTypeReference<T> responseType) {
        return restClient.get().uri(uri).retrieve().body(responseType);
    }

    /**
     * Ejecuta una petición HTTP POST con cuerpo y deserializa la respuesta al tipo indicado.
     *
     * @param uri ruta o URI relativa al {@code baseUrl} configurado
     * @param body cuerpo que será serializado y enviado al servicio remoto
     * @param responseType tipo concreto esperado en la respuesta
     * @param <T> tipo del cuerpo de respuesta
     * @return cuerpo de respuesta deserializado
     */
    public <T> T post(String uri, Object body, Class<T> responseType) {
        return restClient.post().uri(uri).body(body).retrieve().body(responseType);
    }

    /**
     * Ejecuta una petición HTTP POST con cuerpo y deserializa la respuesta usando un tipo
     * parametrizado.
     *
     * @param uri ruta o URI relativa al {@code baseUrl} configurado
     * @param body cuerpo que será serializado y enviado al servicio remoto
     * @param responseType definición del tipo parametrizado esperado en la respuesta
     * @param <T> tipo del cuerpo de respuesta
     * @return cuerpo de respuesta deserializado
     */
    public <T> T post(String uri, Object body, ParameterizedTypeReference<T> responseType) {
        return restClient.post().uri(uri).body(body).retrieve().body(responseType);
    }

    /**
     * Ejecuta una petición HTTP PUT con cuerpo y deserializa la respuesta al tipo indicado.
     *
     * @param uri ruta o URI relativa al {@code baseUrl} configurado
     * @param body cuerpo que será serializado y enviado al servicio remoto
     * @param responseType tipo concreto esperado en la respuesta
     * @param <T> tipo del cuerpo de respuesta
     * @return cuerpo de respuesta deserializado
     */
    public <T> T put(String uri, Object body, Class<T> responseType) {
        return restClient.put().uri(uri).body(body).retrieve().body(responseType);
    }

    /**
     * Ejecuta una petición HTTP PATCH con cuerpo y deserializa la respuesta al tipo indicado.
     *
     * @param uri ruta o URI relativa al {@code baseUrl} configurado
     * @param body cuerpo que será serializado y enviado al servicio remoto
     * @param responseType tipo concreto esperado en la respuesta
     * @param <T> tipo del cuerpo de respuesta
     * @return cuerpo de respuesta deserializado
     */
    public <T> T patch(String uri, Object body, Class<T> responseType) {
        return restClient.patch().uri(uri).body(body).retrieve().body(responseType);
    }

    /**
     * Ejecuta una petición HTTP DELETE sin esperar cuerpo de respuesta.
     *
     * @param uri ruta o URI relativa al {@code baseUrl} configurado
     */
    public void delete(String uri) {
        restClient.delete().uri(uri).retrieve().toBodilessEntity();
    }
}
