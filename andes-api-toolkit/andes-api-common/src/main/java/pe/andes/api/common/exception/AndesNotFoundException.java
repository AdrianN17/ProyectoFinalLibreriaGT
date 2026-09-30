package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.List;

/**
 * Excepción que representa la ausencia del recurso solicitado o de una relación
 * necesaria para completar la operación. Se mapea por defecto al estado HTTP 404.
 */
public class AndesNotFoundException extends AndesApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Código de error estable para recursos no encontrados. */
    public static final String ERROR_CODE = "NOT_FOUND";
    /** Estado HTTP por defecto expuesto para recursos inexistentes. */
    public static final int HTTP_STATUS = 404;

    /**
     * Crea una excepción de recurso no encontrado con el mensaje principal.
     *
     * @param message descripción del recurso o relación no encontrada
     */
    public AndesNotFoundException(String message) {
        this(message, null, null, null);
    }

    /**
     * Crea una excepción de recurso no encontrado asociando la causa original.
     *
     * @param message descripción del recurso o relación no encontrada
     * @param cause causa original del incidente
     */
    public AndesNotFoundException(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    /**
     * Crea una excepción de recurso no encontrado con detalle estructurado y trazabilidad.
     *
     * @param message descripción del recurso o relación no encontrada
     * @param details detalle opcional del error para el consumidor
     * @param traceId identificador de traza relacionado con la operación
     * @param cause causa original del incidente
     */
    public AndesNotFoundException(String message, List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, message, details, traceId, cause);
    }
}
