package pe.andes.api.common.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Define el sobre estándar de respuesta utilizado por controladores y clientes
 * para transportar datos exitosos, errores y metadatos de manera uniforme.
 *
 * <pre>{@code
 * {
 *   "success": true,
 *   "data": {},
 *   "error": null,
 *   "metadata": { "traceId": "..." }
 * }
 * }</pre>
 *
 * @param <T> tipo del contenido transportado en la propiedad {@code data}
 */
public final class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Indica si la operación representada por la respuesta fue satisfactoria.
     */
    private final boolean success;
    /**
     * Carga útil de negocio devuelta al consumidor cuando la operación culmina correctamente.
     */
    private final T data;
    /**
     * Error estructurado devuelto cuando la operación falla.
     */
    private final ApiError error;
    /**
     * Metadatos transversales de trazabilidad, tiempo y versión asociados a la respuesta.
     */
    private final ApiMetadata metadata;

    /**
     * Construye un sobre de respuesta estándar.
     *
     * @param success indica si la operación fue exitosa
     * @param data carga útil de negocio para respuestas exitosas
     * @param error carga de error para respuestas fallidas
     * @param metadata metadatos de trazabilidad y diagnóstico
     */
    public ApiResponse(boolean success, T data, ApiError error, ApiMetadata metadata) {
        this.success = success;
        this.data = data;
        this.error = error;
        this.metadata = metadata;
    }

    /**
     * Crea una respuesta exitosa sin metadatos adicionales.
     *
     * @param data carga útil de negocio
     * @param <T> tipo de la carga útil
     * @return respuesta marcada como exitosa
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, null);
    }

    /**
     * Crea una respuesta exitosa con metadatos asociados.
     *
     * @param data carga útil de negocio
     * @param metadata metadatos de trazabilidad y diagnóstico
     * @param <T> tipo de la carga útil
     * @return respuesta marcada como exitosa
     */
    public static <T> ApiResponse<T> success(T data, ApiMetadata metadata) {
        return new ApiResponse<>(true, data, null, metadata);
    }

    /**
     * Crea una respuesta fallida sin metadatos adicionales.
     *
     * @param error error estructurado a exponer al consumidor
     * @param <T> tipo de la carga útil, normalmente no utilizada en errores
     * @return respuesta marcada como fallida
     */
    public static <T> ApiResponse<T> error(ApiError error) {
        return new ApiResponse<>(false, null, error, null);
    }

    /**
     * Crea una respuesta fallida con metadatos asociados.
     *
     * @param error error estructurado a exponer al consumidor
     * @param metadata metadatos de trazabilidad y diagnóstico
     * @param <T> tipo de la carga útil, normalmente no utilizada en errores
     * @return respuesta marcada como fallida
     */
    public static <T> ApiResponse<T> error(ApiError error, ApiMetadata metadata) {
        return new ApiResponse<>(false, null, error, metadata);
    }

    /**
     * Indica si la respuesta representa una operación exitosa.
     *
     * @return {@code true} si la operación fue exitosa; de lo contrario, {@code false}
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Devuelve la carga útil de negocio transportada por la respuesta.
     *
     * @return datos de la respuesta o {@code null} si se trata de un error
     */
    public T getData() {
        return data;
    }

    /**
     * Devuelve el error estructurado asociado a la respuesta.
     *
     * @return error de la respuesta o {@code null} si fue exitosa
     */
    public ApiError getError() {
        return error;
    }

    /**
     * Devuelve los metadatos de trazabilidad y diagnóstico de la respuesta.
     *
     * @return metadatos asociados o {@code null}
     */
    public ApiMetadata getMetadata() {
        return metadata;
    }

    /**
     * Compara esta respuesta con otra según el contrato observable expuesto por la API.
     *
     * @param o objeto contra el que se realizará la comparación
     * @return {@code true} si ambas respuestas contienen el mismo estado, datos y metadatos
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiResponse<?> that)) {
            return false;
        }
        return success == that.success
                && Objects.equals(data, that.data)
                && Objects.equals(error, that.error)
                && Objects.equals(metadata, that.metadata);
    }

    /**
     * Calcula el código hash consistente con la semántica de igualdad de la respuesta.
     *
     * @return código hash de la respuesta
     */
    @Override
    public int hashCode() {
        return Objects.hash(success, data, error, metadata);
    }

    /**
     * Genera una representación textual útil para trazas y depuración.
     *
     * @return representación textual de la respuesta
     */
    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", data=" + data +
                ", error=" + error +
                ", metadata=" + metadata +
                '}';
    }
}
