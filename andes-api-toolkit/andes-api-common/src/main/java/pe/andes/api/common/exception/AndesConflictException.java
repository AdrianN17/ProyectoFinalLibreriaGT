package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.List;

/**
 * Excepción que representa conflictos con el estado actual del recurso o con
 * una restricción de negocio. Se mapea por defecto al estado HTTP 409.
 */
public class AndesConflictException extends AndesApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Código de error estable para conflictos de negocio o concurrencia. */
    public static final String ERROR_CODE = "CONFLICT";
    /** Estado HTTP por defecto expuesto para conflictos de recurso. */
    public static final int HTTP_STATUS = 409;

    /**
     * Crea una excepción de conflicto con el mensaje funcional principal.
     *
     * @param message descripción del conflicto detectado
     */
    public AndesConflictException(String message) {
        this(message, null, null, null);
    }

    /**
     * Crea una excepción de conflicto asociando la causa original.
     *
     * @param message descripción del conflicto detectado
     * @param cause causa original del incidente
     */
    public AndesConflictException(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    /**
     * Crea una excepción de conflicto con detalle estructurado y trazabilidad.
     *
     * @param message descripción del conflicto detectado
     * @param details detalle opcional del error para el consumidor
     * @param traceId identificador de traza relacionado con la operación
     * @param cause causa original del incidente
     */
    public AndesConflictException(String message, List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, message, details, traceId, cause);
    }
}
