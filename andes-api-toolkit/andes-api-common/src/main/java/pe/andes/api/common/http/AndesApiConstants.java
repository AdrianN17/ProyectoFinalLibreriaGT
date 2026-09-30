package pe.andes.api.common.http;

/**
 * Agrupa constantes transversales compartidas entre los módulos cliente y
 * servidor, como prefijos de configuración y claves de trazabilidad en MDC.
 */
public final class AndesApiConstants {

    private AndesApiConstants() {
    }

    /** Prefijo raíz utilizado por la configuración del toolkit Andes API. */
    public static final String CONFIG_PREFIX = "andes.api";
    /** Clave MDC empleada para propagar el identificador de correlación. */
    public static final String MDC_CORRELATION_ID = "correlationId";
    /** Clave MDC empleada para propagar el identificador de solicitud. */
    public static final String MDC_REQUEST_ID = "requestId";
    /** Clave MDC empleada para propagar el identificador de traza. */
    public static final String MDC_TRACE_ID = "traceId";
    /** Versión de API usada por defecto cuando no se especifica otra. */
    public static final String DEFAULT_API_VERSION = "v1";
}
