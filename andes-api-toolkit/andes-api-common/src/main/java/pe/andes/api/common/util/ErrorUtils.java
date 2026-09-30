package pe.andes.api.common.util;

import pe.andes.api.common.exception.AndesApiException;
import pe.andes.api.common.model.ApiError;

import java.time.Instant;

/**
 * Reúne utilidades puras para transformar excepciones y datos básicos en
 * instancias uniformes de {@link ApiError} listas para ser serializadas.
 */
public final class ErrorUtils {

    private ErrorUtils() {
    }

    /**
     * Convierte una {@link AndesApiException} en un {@link ApiError} preservando
     * el código, estado, detalle y la mejor traza disponible.
     *
     * @param ex excepción Andes a convertir
     * @param traceId identificador de traza a priorizar; si es {@code null}, se usa el de la excepción
     * @return error API equivalente a la excepción recibida
     */
    public static ApiError toApiError(AndesApiException ex, String traceId) {
        return ApiError.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .httpStatus(ex.getHttpStatus())
                .traceId(traceId != null ? traceId : ex.getTraceId())
                .timestamp(Instant.now())
                .details(ex.getDetails())
                .build();
    }

    /**
     * Construye un {@link ApiError} a partir de valores explícitos sin requerir una excepción.
     *
     * @param code código estable del error
     * @param httpStatus estado HTTP asociado al error
     * @param message mensaje principal del error
     * @param traceId identificador de traza asociado a la operación
     * @return error API construido con los valores proporcionados
     */
    public static ApiError toApiError(String code, int httpStatus, String message, String traceId) {
        return ApiError.builder()
                .code(code)
                .message(message)
                .httpStatus(httpStatus)
                .traceId(traceId)
                .timestamp(Instant.now())
                .build();
    }
}
