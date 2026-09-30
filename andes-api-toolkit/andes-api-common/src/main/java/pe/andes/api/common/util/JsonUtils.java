package pe.andes.api.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Centraliza la serialización y deserialización JSON usando un único
 * {@link ObjectMapper} compartido y seguro para uso concurrente.
 */
public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new ParameterNamesModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JsonUtils() {
    }

    /**
     * Serializa un objeto arbitrario a su representación JSON.
     *
     * @param value objeto a serializar
     * @return representación JSON del objeto recibido
     * @throws IllegalStateException si ocurre un error de serialización
     */
    public static String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to serialize object to JSON", e);
        }
    }

    /**
     * Deserializa una cadena JSON al tipo objetivo indicado.
     *
     * @param json contenido JSON a deserializar
     * @param type clase objetivo del resultado
     * @param <T> tipo del objeto esperado
     * @return instancia deserializada del tipo solicitado
     * @throws IllegalStateException si ocurre un error de deserialización
     */
    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to deserialize JSON to " + type.getName(), e);
        }
    }

    /**
     * Devuelve el {@link ObjectMapper} compartido configurado por el toolkit.
     *
     * @return mapper JSON compartido y reutilizable
     */
    public static ObjectMapper sharedObjectMapper() {
        return OBJECT_MAPPER;
    }
}
