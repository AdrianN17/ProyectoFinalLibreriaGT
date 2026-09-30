package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.List;

/**
 * Excepción que representa solicitudes mal formadas o que incumplen una
 * precondición de entrada. Se mapea por defecto al estado HTTP 400.
 */
public class AndesBadRequestException extends AndesApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Código de error estable para solicitudes inválidas. */
    public static final String ERROR_CODE = "BAD_REQUEST";
    /** Estado HTTP por defecto expuesto para solicitudes inválidas. */
    public static final int HTTP_STATUS = 400;

    /**
     * Crea una excepción de solicitud inválida con el mensaje principal.
     *
     * @param message descripción de la condición inválida detectada
     */
    public AndesBadRequestException(String message) {
        this(message, null, null, null);
    }

    /**
     * Crea una excepción de solicitud inválida asociando la causa original.
     *
     * @param message descripción de la condición inválida detectada
     * @param cause causa original del incidente
     */
    public AndesBadRequestException(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    /**
     * Crea una excepción de solicitud inválida con detalle estructurado y trazabilidad.
     *
     * @param message descripción de la condición inválida detectada
     * @param details detalle opcional del error para el consumidor
     * @param traceId identificador de traza relacionado con la operación
     * @param cause causa original del incidente
     */
    public AndesBadRequestException(String message, List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, message, details, traceId, cause);
    }
}
