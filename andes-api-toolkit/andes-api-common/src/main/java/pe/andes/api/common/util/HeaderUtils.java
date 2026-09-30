package pe.andes.api.common.util;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Proporciona utilidades puras para validar y generar identificadores de
 * correlación y solicitud usados en las cabeceras del toolkit.
 */
public final class HeaderUtils {

    private static final Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z0-9\\-]{8,64}$");

    private HeaderUtils() {
    }

    /**
     * Verifica si un identificador de correlación cumple el formato esperado.
     *
     * @param value identificador a validar
     * @return {@code true} si el valor es válido para correlación
     */
    public static boolean isValidCorrelationId(String value) {
        return value != null && ID_PATTERN.matcher(value).matches();
    }

    /**
     * Verifica si un identificador de solicitud cumple el formato esperado.
     *
     * @param value identificador a validar
     * @return {@code true} si el valor es válido para solicitud
     */
    public static boolean isValidRequestId(String value) {
        return value != null && ID_PATTERN.matcher(value).matches();
    }

    /**
     * Genera un identificador de correlación nuevo en formato UUID.
     *
     * @return identificador de correlación generado
     */
    public static String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Genera un identificador de solicitud nuevo en formato UUID.
     *
     * @return identificador de solicitud generado
     */
    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Devuelve el valor recibido si ya fue validado; en caso contrario genera
     * un nuevo identificador de correlación como valor de respaldo.
     *
     * @param value valor original evaluado
     * @param valid resultado de la validación previa del valor
     * @return valor original si es válido; de lo contrario, uno nuevo generado
     */
    public static String defaultIfInvalid(String value, boolean valid) {
        return valid ? value : generateCorrelationId();
    }
}
