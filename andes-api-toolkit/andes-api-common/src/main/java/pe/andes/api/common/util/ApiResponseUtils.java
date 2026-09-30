package pe.andes.api.common.util;

import pe.andes.api.common.model.ApiError;
import pe.andes.api.common.model.ApiMetadata;
import pe.andes.api.common.model.ApiResponse;

/**
 * Proporciona atajos puros para construir sobres {@link ApiResponse} de forma
 * consistente y expresiva desde cualquier capa del toolkit.
 */
public final class ApiResponseUtils {

    private ApiResponseUtils() {
    }

    /**
     * Construye una respuesta exitosa con datos y metadatos asociados.
     *
     * @param data carga útil de negocio
     * @param metadata metadatos de trazabilidad y diagnóstico
     * @param <T> tipo de la carga útil
     * @return respuesta exitosa construida
     */
    public static <T> ApiResponse<T> ok(T data, ApiMetadata metadata) {
        return ApiResponse.success(data, metadata);
    }

    /**
     * Construye una respuesta exitosa con datos y sin metadatos adicionales.
     *
     * @param data carga útil de negocio
     * @param <T> tipo de la carga útil
     * @return respuesta exitosa construida
     */
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.success(data);
    }

    /**
     * Construye una respuesta fallida con error estructurado y metadatos asociados.
     *
     * @param error error estructurado a exponer al consumidor
     * @param metadata metadatos de trazabilidad y diagnóstico
     * @param <T> tipo de la carga útil, normalmente no utilizada en errores
     * @return respuesta fallida construida
     */
    public static <T> ApiResponse<T> fail(ApiError error, ApiMetadata metadata) {
        return ApiResponse.error(error, metadata);
    }

    /**
     * Construye una respuesta fallida con error estructurado y sin metadatos adicionales.
     *
     * @param error error estructurado a exponer al consumidor
     * @param <T> tipo de la carga útil, normalmente no utilizada en errores
     * @return respuesta fallida construida
     */
    public static <T> ApiResponse<T> fail(ApiError error) {
        return ApiResponse.error(error);
    }

    /**
     * Determina si una respuesta Andes representa una operación exitosa.
     *
     * @param response respuesta a evaluar
     * @return {@code true} si la respuesta no es nula y fue exitosa
     */
    public static boolean isSuccessful(ApiResponse<?> response) {
        return response != null && response.isSuccess();
    }
}
