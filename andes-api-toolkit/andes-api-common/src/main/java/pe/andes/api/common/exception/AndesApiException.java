package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.Collections;
import java.util.List;

/**
 * Excepción base de la jerarquía de errores funcionales y técnicos de Andes API.
 * Cada subtipo encapsula un código de error estable, el estado HTTP que debe
 * exponerse en la superficie de la API y, de forma opcional, el detalle fino
 * de validaciones o reglas de negocio representado por {@link ApiErrorDetail}.
 *
 * <pre>
 * AndesApiException
 * |- AndesValidationException
 * |- AndesAuthenticationException
 * |- AndesAuthorizationException
 * |- AndesNotFoundException
 * |- AndesConflictException
 * |- AndesBadRequestException
 * `- AndesRemoteServiceException
 * </pre>
 */
public abstract class AndesApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final int httpStatus;
    private final List<ApiErrorDetail> details;
    private final String traceId;

    /**
     * Inicializa la excepción base con la información que posteriormente será
     * transformada en una respuesta de error uniforme.
     *
     * @param errorCode código funcional o técnico que identifica el tipo de error
     * @param httpStatus estado HTTP que representa la excepción en la API
     * @param message mensaje principal que describe la causa del error
     * @param details detalle opcional de validaciones o incumplimientos detectados
     * @param traceId identificador de trazabilidad asociado a la operación fallida
     * @param cause causa original del fallo, si existe
     */
    protected AndesApiException(String errorCode, int httpStatus, String message,
                                 List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.details = details != null ? List.copyOf(details) : Collections.emptyList();
        this.traceId = traceId;
    }

    /**
     * Devuelve el código de error estable que identifica la categoría de la excepción.
     *
     * @return código de error de la excepción
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Devuelve el estado HTTP que debe emplearse al convertir esta excepción en respuesta.
     *
     * @return estado HTTP asociado a la excepción
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * Devuelve el detalle estructurado asociado al error.
     *
     * @return lista inmutable de detalles del error
     */
    public List<ApiErrorDetail> getDetails() {
        return details;
    }

    /**
     * Devuelve el identificador de traza vinculado al error, si fue proporcionado.
     *
     * @return identificador de traza o {@code null}
     */
    public String getTraceId() {
        return traceId;
    }
}
