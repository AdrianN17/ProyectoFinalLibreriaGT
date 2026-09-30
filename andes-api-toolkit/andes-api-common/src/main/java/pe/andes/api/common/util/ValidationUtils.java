package pe.andes.api.common.util;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Proporciona validaciones utilitarias sin dependencias externas para cadenas,
 * colecciones y formatos reutilizados por el toolkit.
 */
public final class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private ValidationUtils() {
    }

    /**
     * Determina si una cadena es nula, vacía o contiene solo espacios.
     *
     * @param value valor a evaluar
     * @return {@code true} si la cadena no contiene texto significativo
     */
    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    /**
     * Determina si una cadena contiene texto significativo.
     *
     * @param value valor a evaluar
     * @return {@code true} si la cadena no es nula ni está en blanco
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /**
     * Determina si una colección es nula o no contiene elementos.
     *
     * @param collection colección a evaluar
     * @return {@code true} si la colección es nula o está vacía
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Exige que una cadena contenga texto significativo y devuelve el mismo valor.
     *
     * @param value valor a validar
     * @param message mensaje usado en la excepción si la validación falla
     * @return el valor recibido cuando cumple la validación
     * @throws IllegalArgumentException si el valor es nulo o está en blanco
     */
    public static String requireNonBlank(String value, String message) {
        if (isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Determina si una cadena representa un correo electrónico con formato válido.
     *
     * @param value correo electrónico a validar
     * @return {@code true} si el correo tiene formato válido
     */
    public static boolean isValidEmail(String value) {
        return isNotBlank(value) && EMAIL_PATTERN.matcher(value).matches();
    }
}
