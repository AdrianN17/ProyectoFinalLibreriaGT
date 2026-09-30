package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.List;

/**
 * Excepción que representa el incumplimiento de validaciones Bean Validation o
 * reglas de negocio de dominio. Se mapea por defecto al estado HTTP 422.
 */
public class AndesValidationException extends AndesApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Código de error estable para validaciones rechazadas. */
    public static final String ERROR_CODE = "VALIDATION_ERROR";
    /** Estado HTTP por defecto expuesto para errores de validación. */
    public static final int HTTP_STATUS = 422;

    /**
     * Crea una excepción de validación con el mensaje funcional principal.
     *
     * @param message descripción de la validación incumplida
     */
    public AndesValidationException(String message) {
        this(message, null, null, null);
    }

    /**
     * Crea una excepción de validación incluyendo el detalle puntual de cada incidencia.
     *
     * @param message descripción de la validación incumplida
     * @param details detalle de errores de validación o negocio detectados
     */
    public AndesValidationException(String message, List<ApiErrorDetail> details) {
        this(message, details, null, null);
    }

    /**
     * Crea una excepción de validación con detalle estructurado y trazabilidad.
     *
     * @param message descripción de la validación incumplida
     * @param details detalle de errores de validación o negocio detectados
     * @param traceId identificador de traza relacionado con la operación
     * @param cause causa original del incidente
     */
    public AndesValidationException(String message, List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, message, details, traceId, cause);
    }
}
