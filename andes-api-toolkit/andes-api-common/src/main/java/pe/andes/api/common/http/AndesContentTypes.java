package pe.andes.api.common.http;

/**
 * Centraliza los tipos MIME utilizados de forma recurrente por los módulos
 * cliente y servidor del toolkit Andes API.
 */
public final class AndesContentTypes {

    private AndesContentTypes() {
    }

    /** Tipo MIME para cargas y respuestas JSON estándar. */
    public static final String APPLICATION_JSON = "application/json";
    /** Tipo MIME para errores alineados con RFC 7807. */
    public static final String APPLICATION_PROBLEM_JSON = "application/problem+json";
    /** Tipo MIME para representaciones XML. */
    public static final String APPLICATION_XML = "application/xml";
    /** Tipo MIME para respuestas de texto plano. */
    public static final String TEXT_PLAIN = "text/plain";
}
