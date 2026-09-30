package pe.andes.api.client;

import java.util.Map;

/**
 * Registro inmutable de clientes Andes disponibles en la aplicación. La autoconfiguración llena
 * este contenedor con una instancia de {@link AndesApiClient} por cada entrada declarada en
 * configuración, de modo que componentes de infraestructura o negocio puedan resolver un cliente
 * por su nombre lógico cuando no conviene inyectarlo de manera individual.
 */
public class AndesApiClientRegistry {

    private final Map<String, AndesApiClient> clients;

    /**
     * Crea el registro a partir del mapa de clientes configurados.
     *
     * @param clients clientes indexados por su nombre lógico
     */
    public AndesApiClientRegistry(Map<String, AndesApiClient> clients) {
        this.clients = Map.copyOf(clients);
    }

    /**
     * Obtiene un cliente registrado por su nombre lógico.
     *
     * @param name nombre del cliente configurado
     * @return cliente Andes asociado al nombre indicado
     * @throws IllegalArgumentException si no existe un cliente registrado con ese nombre
     */
    public AndesApiClient get(String name) {
        AndesApiClient client = clients.get(name);
        if (client == null) {
            throw new IllegalArgumentException("No Andes API client configured with name: " + name);
        }
        return client;
    }

    /**
     * Devuelve la vista completa de clientes registrados.
     *
     * @return mapa inmutable con todos los clientes disponibles
     */
    public Map<String, AndesApiClient> getAll() {
        return clients;
    }
}
