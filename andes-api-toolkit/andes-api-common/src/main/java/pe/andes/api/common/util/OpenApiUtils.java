package pe.andes.api.common.util;

import java.util.regex.Pattern;

/**
 * Reúne validaciones y normalizaciones relacionadas con convenciones OpenAPI,
 * como {@code operationId} y versionado semántico.
 */
public final class OpenApiUtils {

    private static final Pattern OPERATION_ID_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$");
    private static final Pattern SEMVER_PATTERN =
            Pattern.compile("^\\d+\\.\\d+\\.\\d+(-[0-9A-Za-z.-]+)?(\\+[0-9A-Za-z.-]+)?$");

    private OpenApiUtils() {
    }

    /**
     * Verifica si un {@code operationId} cumple la convención definida por el toolkit.
     *
     * @param operationId identificador de operación a validar
     * @return {@code true} si el identificador cumple el formato esperado
     */
    public static boolean isValidOperationId(String operationId) {
        return operationId != null && OPERATION_ID_PATTERN.matcher(operationId).matches();
    }

    /**
     * Verifica si una versión cumple el formato de versionado semántico.
     *
     * @param version versión a validar
     * @return {@code true} si la versión respeta SemVer
     */
    public static boolean isValidSemanticVersion(String version) {
        return version != null && SEMVER_PATTERN.matcher(version).matches();
    }

    /**
     * Normaliza una versión de API aplicando recorte de espacios y un valor por defecto.
     *
     * @param rawVersion versión recibida de forma externa
     * @param defaultVersion versión a usar cuando la entrada llegue vacía
     * @return versión normalizada lista para propagarse
     */
    public static String normalizeApiVersion(String rawVersion, String defaultVersion) {
        return ValidationUtils.isBlank(rawVersion) ? defaultVersion : rawVersion.trim();
    }
}
