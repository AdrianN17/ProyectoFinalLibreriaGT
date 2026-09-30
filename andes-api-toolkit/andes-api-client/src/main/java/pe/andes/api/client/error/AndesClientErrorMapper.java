package pe.andes.api.client.error;

import pe.andes.api.common.exception.AndesApiException;
import pe.andes.api.common.exception.AndesAuthenticationException;
import pe.andes.api.common.exception.AndesAuthorizationException;
import pe.andes.api.common.exception.AndesBadRequestException;
import pe.andes.api.common.exception.AndesConflictException;
import pe.andes.api.common.exception.AndesNotFoundException;
import pe.andes.api.common.exception.AndesRemoteServiceException;
import pe.andes.api.common.exception.AndesValidationException;
import pe.andes.api.common.model.ApiError;
import pe.andes.api.common.model.ApiErrorDetail;
import pe.andes.api.common.util.JsonUtils;

import java.util.List;

/**
 * Traduce errores HTTP devueltos por servicios remotos a la jerarquía de excepciones común de
 * Andes. Esta clase es utilizada por {@link pe.andes.api.client.web.AndesRestClientFactory} para
 * conectar el manejo de estados erróneos del {@link org.springframework.web.client.RestClient} con
 * excepciones de dominio reutilizables por el consumidor del cliente.
 *
 * <pre>
 * 404 -&gt; AndesNotFoundException        401 -&gt; AndesAuthenticationException
 * 403 -&gt; AndesAuthorizationException   409 -&gt; AndesConflictException
 * 400 -&gt; AndesBadRequestException      422 -&gt; AndesValidationException
 * 5xx / otros -&gt; AndesRemoteServiceException
 * </pre>
 *
 * Si el cuerpo remoto respeta el contrato {@link ApiError}, se conservan su código, mensaje,
 * detalles y trace id. En caso contrario, se construye un mensaje genérico de mejor esfuerzo.
 */
public class AndesClientErrorMapper {

    /**
     * Convierte un error HTTP remoto en una excepción Andes específica según el código de estado.
     *
     * @param endpoint endpoint o URI que produjo el error
     * @param statusCode código de estado HTTP recibido
     * @param responseBody cuerpo de respuesta recibido desde el servicio remoto
     * @return excepción Andes equivalente al error remoto
     */
    public AndesApiException map(String endpoint, int statusCode, String responseBody) {
        ApiError remoteError = tryParse(responseBody);
        String message = remoteError != null && remoteError.getMessage() != null
                ? remoteError.getMessage()
                : "Remote call to " + endpoint + " failed with status " + statusCode;
        List<ApiErrorDetail> details = remoteError != null ? remoteError.getDetails() : List.of();
        String traceId = remoteError != null ? remoteError.getTraceId() : null;

        return switch (statusCode) {
            case 400 -> new AndesBadRequestException(message, details, traceId, null);
            case 401 -> new AndesAuthenticationException(message, details, traceId, null);
            case 403 -> new AndesAuthorizationException(message, details, traceId, null);
            case 404 -> new AndesNotFoundException(message, details, traceId, null);
            case 409 -> new AndesConflictException(message, details, traceId, null);
            case 422 -> new AndesValidationException(message, details, traceId, null);
            default -> new AndesRemoteServiceException(message, endpoint, statusCode, details, traceId, null);
        };
    }

    /**
     * Intenta deserializar el cuerpo remoto al contrato estándar de error de Andes.
     *
     * @param body cuerpo de respuesta recibido
     * @return error deserializado o {@code null} si el contenido no puede interpretarse
     */
    private ApiError tryParse(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            return JsonUtils.fromJson(body, ApiError.class);
        } catch (Exception e) {
            return null;
        }
    }
}
