package pe.andes.api.common.http;

/**
 * Reúne códigos de estado HTTP en una forma agnóstica del framework para que
 * {@code andes-api-common} no dependa de tipos específicos de Spring u otros stacks.
 */
public final class AndesHttpStatus {

    private AndesHttpStatus() {
    }

    /** Estado HTTP 200 para operaciones exitosas. */
    public static final int OK = 200;
    /** Estado HTTP 201 para recursos creados satisfactoriamente. */
    public static final int CREATED = 201;
    /** Estado HTTP 204 para operaciones exitosas sin cuerpo de respuesta. */
    public static final int NO_CONTENT = 204;
    /** Estado HTTP 400 para solicitudes inválidas o mal formadas. */
    public static final int BAD_REQUEST = 400;
    /** Estado HTTP 401 para solicitudes sin autenticación válida. */
    public static final int UNAUTHORIZED = 401;
    /** Estado HTTP 403 para accesos autenticados pero no autorizados. */
    public static final int FORBIDDEN = 403;
    /** Estado HTTP 404 para recursos inexistentes. */
    public static final int NOT_FOUND = 404;
    /** Estado HTTP 409 para conflictos con el estado actual del recurso. */
    public static final int CONFLICT = 409;
    /** Estado HTTP 422 para validaciones o reglas de negocio incumplidas. */
    public static final int UNPROCESSABLE_ENTITY = 422;
    /** Estado HTTP 500 para errores internos no controlados. */
    public static final int INTERNAL_SERVER_ERROR = 500;
    /** Estado HTTP 502 para fallos provenientes de un servicio aguas abajo. */
    public static final int BAD_GATEWAY = 502;
    /** Estado HTTP 503 para indisponibilidad temporal del servicio. */
    public static final int SERVICE_UNAVAILABLE = 503;
    /** Estado HTTP 504 para expiración de tiempo de espera en una integración. */
    public static final int GATEWAY_TIMEOUT = 504;
}
