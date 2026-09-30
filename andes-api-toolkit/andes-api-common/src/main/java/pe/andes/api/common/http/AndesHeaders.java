package pe.andes.api.common.http;

/**
 * Centraliza los nombres de cabeceras HTTP que el toolkit Andes API utiliza
 * para versionado, autenticación y trazabilidad entre servicios.
 */
public final class AndesHeaders {

    private AndesHeaders() {
    }

    /** Cabecera que transporta el identificador de correlación extremo a extremo. */
    public static final String CORRELATION_ID = "X-Correlation-Id";
    /** Cabecera que identifica de forma única una solicitud concreta. */
    public static final String REQUEST_ID = "X-Request-Id";
    /** Cabecera que comunica la versión de API solicitada o respondida. */
    public static final String API_VERSION = "X-Api-Version";
    /** Cabecera que expone el identificador de traza asociado a la operación. */
    public static final String TRACE_ID = "X-Trace-Id";
    /** Cabecera estándar que describe el tipo de contenido del cuerpo HTTP. */
    public static final String CONTENT_TYPE = "Content-Type";
    /** Cabecera estándar que indica los tipos de respuesta aceptados. */
    public static final String ACCEPT = "Accept";
    /** Cabecera estándar para credenciales de autenticación. */
    public static final String AUTHORIZATION = "Authorization";
}
