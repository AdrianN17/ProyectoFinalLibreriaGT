package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.List;

/**
 * Excepción que indica que el solicitante ya fue autenticado, pero no cuenta
 * con permisos suficientes para ejecutar la operación requerida. Se mapea al
 * estado HTTP 403.
 */
public class AndesAuthorizationException extends AndesApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Código de error estable para denegaciones de autorización. */
    public static final String ERROR_CODE = "AUTHORIZATION_ERROR";
    /** Estado HTTP por defecto expuesto para errores de autorización. */
    public static final int HTTP_STATUS = 403;

    /**
     * Crea una excepción de autorización con el mensaje funcional principal.
     *
     * @param message descripción de la restricción de acceso detectada
     */
    public AndesAuthorizationException(String message) {
        this(message, null, null, null);
    }

    /**
     * Crea una excepción de autorización asociando la causa original.
     *
     * @param message descripción de la restricción de acceso detectada
     * @param cause causa original del incidente
     */
    public AndesAuthorizationException(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    /**
     * Crea una excepción de autorización con detalle estructurado y trazabilidad.
     *
     * @param message descripción de la restricción de acceso detectada
     * @param details detalle opcional del error para el consumidor
     * @param traceId identificador de traza relacionado con la operación
     * @param cause causa original del incidente
     */
    public AndesAuthorizationException(String message, List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, message, details, traceId, cause);
    }
}
