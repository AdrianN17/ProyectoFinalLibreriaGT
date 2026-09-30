package pe.andes.lib.text;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Utilidades para normalizar texto libre en identificadores legibles y estables ("slugs"),
 * y para truncar cadenas largas de forma segura.
 *
 * <p>Clase clasica de Java puro: {@code final}, sin estado, sin dependencias externas y con
 * un unico constructor privado, de modo que solo se puede invocar a traves de sus metodos
 * {@code static}. Puede usarse en cualquier proyecto Java (con o sin Spring).</p>
 */
public final class SlugUtils {

    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-z0-9]+");
    private static final Pattern EDGE_DASHES = Pattern.compile("^-+|-+$");
    private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    private SlugUtils() {
        throw new AssertionError("SlugUtils is a static utility class and must not be instantiated");
    }

    /**
     * Convierte un texto arbitrario (con acentos, mayusculas, simbolos) en un slug en
     * minusculas separado por guiones, por ejemplo {@code "Órdenes de Compra #42"} ->
     * {@code "ordenes-de-compra-42"}.
     *
     * @param input texto de entrada; {@code null} se trata como cadena vacia
     * @return slug normalizado, nunca {@code null}
     */
    public static String slugify(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutDiacritics = DIACRITICS.matcher(normalized).replaceAll("");
        String lower = withoutDiacritics.toLowerCase(Locale.ROOT);
        String slug = NON_ALPHANUMERIC.matcher(lower).replaceAll("-");
        return EDGE_DASHES.matcher(slug).replaceAll("");
    }

    /**
     * Trunca una cadena a un maximo de caracteres, agregando un sufijo (por ejemplo {@code "..."})
     * cuando el texto original supera el limite. Nunca lanza excepcion por indices fuera de rango.
     *
     * @param input     texto de entrada; {@code null} se trata como cadena vacia
     * @param maxLength longitud maxima total (incluyendo el sufijo)
     * @param suffix    sufijo a anexar cuando se trunca; {@code null} equivale a cadena vacia
     * @return texto truncado
     */
    public static String truncate(String input, int maxLength, String suffix) {
        if (input == null) {
            return "";
        }
        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength must be >= 0");
        }
        String safeSuffix = suffix == null ? "" : suffix;
        if (input.length() <= maxLength) {
            return input;
        }
        int cutLength = Math.max(0, maxLength - safeSuffix.length());
        return input.substring(0, cutLength) + safeSuffix;
    }
}
