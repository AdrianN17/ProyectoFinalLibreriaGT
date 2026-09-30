package pe.andes.lib.text;

/**
 * Utilidades para enmascarar datos sensibles (correos, tarjetas, telefonos) antes de
 * mostrarlos en logs, UIs o respuestas de error.
 *
 * <p>Clase clasica de Java puro: {@code final}, sin estado, sin dependencias externas y con
 * un unico constructor privado, de modo que solo se puede invocar a traves de sus metodos
 * {@code static}.</p>
 */
public final class MaskUtils {

    private MaskUtils() {
        throw new AssertionError("MaskUtils is a static utility class and must not be instantiated");
    }

    /**
     * Enmascara un correo electronico dejando visible solo el primer caracter del usuario y
     * el dominio completo, por ejemplo {@code "ana@andes.pe"} -> {@code "a**@andes.pe"}.
     *
     * @param email correo a enmascarar
     * @return correo enmascarado, o el mismo valor de entrada si no tiene formato de correo valido
     */
    public static String maskEmail(String email) {
        if (email == null || email.isBlank()) {
            return email;
        }
        int at = email.indexOf('@');
        if (at <= 0) {
            return email;
        }
        String user = email.substring(0, at);
        String domain = email.substring(at);
        String visible = user.substring(0, 1);
        return visible + "*".repeat(Math.max(1, user.length() - 1)) + domain;
    }

    /**
     * Enmascara un numero de tarjeta/documento dejando visibles unicamente los ultimos
     * {@code visibleDigits} caracteres, por ejemplo {@code maskDigits("4111111111111111", 4)}
     * -> {@code "************1111"}.
     *
     * @param digits        cadena numerica a enmascarar
     * @param visibleDigits cantidad de digitos finales que quedan visibles
     * @return cadena enmascarada
     */
    public static String maskDigits(String digits, int visibleDigits) {
        if (digits == null || digits.isBlank()) {
            return digits;
        }
        if (visibleDigits < 0) {
            throw new IllegalArgumentException("visibleDigits must be >= 0");
        }
        int visible = Math.min(visibleDigits, digits.length());
        int masked = digits.length() - visible;
        return "*".repeat(masked) + digits.substring(masked);
    }
}
