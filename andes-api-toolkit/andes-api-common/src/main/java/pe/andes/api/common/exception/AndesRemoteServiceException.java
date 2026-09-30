package pe.andes.api.common.exception;

import pe.andes.api.common.model.ApiErrorDetail;

import java.io.Serial;
import java.util.List;

/**
 * Excepción utilizada principalmente por el cliente Andes para representar fallos
 * de integración con servicios remotos, ya sea por errores 5xx o por incidencias
 * de transporte. Se expone por defecto como HTTP 502, preservando además el
 * endpoint remoto y su estado HTTP original para fines de diagnóstico.
 */
public class AndesRemoteServiceException extends AndesApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Código de error estable para fallos de servicios remotos. */
    public static final String ERROR_CODE = "REMOTE_SERVICE_ERROR";
    /** Estado HTTP por defecto expuesto cuando falla un servicio remoto. */
    public static final int HTTP_STATUS = 502;

    private final String endpoint;
    private final int remoteHttpStatus;

    /**
     * Crea una excepción de servicio remoto conservando el endpoint y el estado
     * HTTP devuelto por el sistema externo.
     *
     * @param message descripción del fallo detectado en la integración remota
     * @param endpoint endpoint remoto involucrado en la operación fallida
     * @param remoteHttpStatus estado HTTP devuelto por el servicio remoto
     * @param cause causa original del incidente
     */
    public AndesRemoteServiceException(String message, String endpoint, int remoteHttpStatus, Throwable cause) {
        this(message, endpoint, remoteHttpStatus, null, null, cause);
    }

    /**
     * Crea una excepción de servicio remoto con detalle estructurado y trazabilidad.
     *
     * @param message descripción del fallo detectado en la integración remota
     * @param endpoint endpoint remoto involucrado en la operación fallida
     * @param remoteHttpStatus estado HTTP devuelto por el servicio remoto
     * @param details detalle opcional del error para el consumidor
     * @param traceId identificador de traza relacionado con la operación
     * @param cause causa original del incidente
     */
    public AndesRemoteServiceException(String message, String endpoint, int remoteHttpStatus,
                                        List<ApiErrorDetail> details, String traceId, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, message, details, traceId, cause);
        this.endpoint = endpoint;
        this.remoteHttpStatus = remoteHttpStatus;
    }

    /**
     * Devuelve el endpoint remoto involucrado en el fallo.
     *
     * @return endpoint remoto relacionado con la excepción
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Devuelve el estado HTTP emitido por el servicio remoto. Este valor es
     * independiente de {@link #getHttpStatus()}, que representa el estado con el
     * que la propia API Andes expone la excepción al consumidor.
     *
     * @return estado HTTP recibido del servicio remoto
     */
    public int getRemoteHttpStatus() {
        return remoteHttpStatus;
    }
}
