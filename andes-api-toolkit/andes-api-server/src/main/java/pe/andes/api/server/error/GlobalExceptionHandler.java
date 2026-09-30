package pe.andes.api.server.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import pe.andes.api.common.exception.AndesApiException;
import pe.andes.api.common.http.AndesApiConstants;
import pe.andes.api.common.model.ApiError;
import pe.andes.api.common.model.ApiErrorDetail;
import pe.andes.api.common.model.ApiResponse;
import pe.andes.api.common.util.ErrorUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Manejador global de excepciones para controladores REST basados en Spring MVC/WebFlux.
 *
 * <p>Al exponerse como {@code @RestControllerAdvice}, intercepta excepciones lanzadas durante el
 * procesamiento de la petición y las transforma en el envoltorio estándar {@code ApiResponse}. El
 * mapeo principal sigue la jerarquía {@code AndesApiException} y complementa escenarios comunes de
 * validación, errores de entrada y fallos inesperados.
 *
 * <pre>
 * 400 -&gt; BadRequest        401 -&gt; Authentication   403 -&gt; Authorization
 * 404 -&gt; NotFound          409 -&gt; Conflict         422 -&gt; Validation
 * 500 -&gt; InternalServerError
 * </pre>
 *
 * <p>Además, integra mapeadores personalizados {@link AndesExceptionMapper} para traducir
 * excepciones de terceros al mismo contrato de error sin duplicar lógica en cada controlador.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final List<AndesExceptionMapper<?>> customMappers;
    private final boolean includeStackTrace;

    /**
     * Crea el manejador global con los adaptadores personalizados registrados en Spring.
     *
     * @param customMappers mapeadores adicionales para excepciones no cubiertas por el toolkit
     * @param includeStackTrace {@code true} si los errores 500 deben exponer detalle técnico
     */
    public GlobalExceptionHandler(List<AndesExceptionMapper<?>> customMappers, boolean includeStackTrace) {
        this.customMappers = customMappers;
        this.includeStackTrace = includeStackTrace;
    }

    /**
     * Traduce excepciones de negocio Andes al código HTTP y cuerpo estandarizado ya definidos por
     * la propia excepción.
     *
     * @param ex excepción de la jerarquía Andes
     * @return respuesta HTTP con el estado y {@code ApiError} derivados de la excepción
     */
    @ExceptionHandler(AndesApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleAndesApiException(AndesApiException ex) {
        log.warn("Handled AndesApiException [{}]: {}", ex.getErrorCode(), ex.getMessage());
        ApiError error = ErrorUtils.toApiError(ex, currentTraceId());
        return ResponseEntity.status(ex.getHttpStatus()).body(ApiResponse.error(error));
    }

    /**
     * Maneja errores de validación de {@code @Valid} sobre cuerpos o formularios enlazados por
     * Spring.
     *
     * @param ex excepción producida por el binding/validation de argumentos
     * @return respuesta 422 con el detalle por campo invalidado
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ApiErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> ApiErrorDetail.of(fe.getField(), fe.getCode(), fe.getDefaultMessage()))
                .toList();
        ApiError error = ApiError.builder()
                .code("VALIDATION_ERROR")
                .message("Request validation failed")
                .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .traceId(currentTraceId())
                .timestamp(Instant.now())
                .details(details)
                .build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.error(error));
    }

    /**
     * Maneja violaciones de restricciones Bean Validation disparadas fuera del binding de cuerpos,
     * por ejemplo sobre parámetros de métodos o validaciones programáticas.
     *
     * @param ex excepción con el conjunto de restricciones incumplidas
     * @return respuesta 422 con el detalle de cada violación
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        List<ApiErrorDetail> details = ex.getConstraintViolations().stream()
                .map(this::toDetail)
                .toList();
        ApiError error = ApiError.builder()
                .code("VALIDATION_ERROR")
                .message("Request validation failed")
                .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .traceId(currentTraceId())
                .timestamp(Instant.now())
                .details(details)
                .build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.error(error));
    }

    /**
     * Maneja errores de entrada reportados por Spring al no poder deserializar o convertir datos
     * de la petición.
     *
     * @param ex excepción que describe la razón del problema de entrada
     * @return respuesta 400 con un mensaje orientado al consumidor
     */
    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ApiResponse<Void>> handleServerWebInput(ServerWebInputException ex) {
        ApiError error = ErrorUtils.toApiError("BAD_REQUEST", HttpStatus.BAD_REQUEST.value(),
                "Malformed request: " + ex.getReason(), currentTraceId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(error));
    }

    /**
     * Punto de captura final para cualquier excepción no manejada explícitamente.
     *
     * <p>Primero intenta delegar la traducción a un {@link AndesExceptionMapper} personalizado y,
     * si ninguno aplica, responde con un error 500 estándar.
     *
     * @param ex excepción no controlada durante el procesamiento de la petición
     * @return respuesta personalizada o un error interno genérico
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        Optional<ResponseEntity<ApiResponse<Void>>> customResult = tryCustomMapper(ex);
        if (customResult.isPresent()) {
            return customResult.get();
        }

        log.error("Unhandled exception", ex);
        String message = includeStackTrace ? ex.toString() : "An unexpected error occurred";
        ApiError error = ErrorUtils.toApiError("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message, currentTraceId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(error));
    }

    @SuppressWarnings("unchecked")
    private Optional<ResponseEntity<ApiResponse<Void>>> tryCustomMapper(Exception ex) {
        for (AndesExceptionMapper<?> mapper : customMappers) {
            if (mapper.getExceptionType().isInstance(ex)) {
                AndesExceptionMapper<Throwable> typed = (AndesExceptionMapper<Throwable>) mapper;
                ApiError error = typed.map(ex, currentTraceId());
                return Optional.of(ResponseEntity.status(mapper.getHttpStatus()).body(ApiResponse.error(error)));
            }
        }
        return Optional.empty();
    }

    private ApiErrorDetail toDetail(ConstraintViolation<?> violation) {
        String field = violation.getPropertyPath() != null ? violation.getPropertyPath().toString() : null;
        return ApiErrorDetail.of(field, violation.getMessage());
    }

    private String currentTraceId() {
        return MDC.get(AndesApiConstants.MDC_CORRELATION_ID);
    }
}
