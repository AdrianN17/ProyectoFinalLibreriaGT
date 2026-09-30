package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.List;

/**
 * Excepción que representa fallos de autenticación, como credenciales ausentes,
 * inválidas o expiradas. Se mapea por defecto al estado HTTP 401.
 */
public class AndesAuthenticationException extends AndesApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Código de error estable para incidentes de autenticación. */
    public static final String ERROR_CODE = "AUTHENTICATION_ERROR";
    /** Estado HTTP por defecto expuesto para errores de autenticación. */
    public static final int HTTP_STATUS = 401;

    /**
     * Crea una excepción de autenticación con el mensaje funcional principal.
     *
     * @param message descripción del fallo de autenticación detectado
     */
    public AndesAuthenticationException(String message) {
        this(message, null, null, null);
    }

    /**
     * Crea una excepción de autenticación asociando la causa original.
     *
     * @param message descripción del fallo de autenticación detectado
     * @param cause causa original del incidente
     */
    public AndesAuthenticationException(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    /**
     * Crea una excepción de autenticación con detalle estructurado y trazabilidad.
     *
     * @param message descripción del fallo de autenticación detectado
     * @param details detalle opcional del error para el consumidor
     * @param traceId identificador de traza relacionado con la operación
     * @param cause causa original del incidente
     */
    public AndesAuthenticationException(String message, List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, message, details, traceId, cause);
    }
}
