package pe.andes.api.server.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pe.andes.api.common.model.ApiMetadata;
import pe.andes.api.common.model.ApiResponse;
import pe.andes.api.common.http.AndesApiConstants;
import org.slf4j.MDC;

/**
 * Adaptador transversal de respuestas exitosas basado en {@link ResponseBodyAdvice}.
 *
 * <p>Spring invoca este componente justo antes de serializar el cuerpo HTTP de un controlador
 * REST. Su responsabilidad es envolver el resultado en {@link ApiResponse} y adjuntar metadatos
 * de trazabilidad tomados del {@link MDC}, de modo que los consumidores reciban un contrato
 * homogéneo sin que cada endpoint tenga que construirlo manualmente.
 */
@RestControllerAdvice
public class AndesResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * Determina si el valor devuelto por el controlador debe pasar por el envoltorio estándar.
     *
     * @param returnType firma del método controlador seleccionada por Spring
     * @param converterType convertidor HTTP que serializará el cuerpo
     * @return {@code true} cuando el tipo declarado no es ya un {@code ApiResponse}
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> type = returnType.getParameterType();
        return !ApiResponse.class.isAssignableFrom(type);
    }

    /**
     * Envuelve el cuerpo exitoso en {@link ApiResponse} e inyecta metadatos de correlación.
     *
     * <p>Si el controlador ya devolvió un {@code ApiResponse}, el valor se retorna intacto para
     * evitar dobles envolturas.
     *
     * @param body cuerpo producido por el controlador
     * @param returnType firma del método controlador
     * @param selectedContentType tipo de contenido negociado
     * @param selectedConverterType convertidor HTTP que escribirá el cuerpo
     * @param request abstracción de la petición actual
     * @param response abstracción de la respuesta actual
     * @return cuerpo original o un {@code ApiResponse} exitoso con metadatos Andes
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                   Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                   ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse<?>) {
            return body;
        }
        ApiMetadata metadata = ApiMetadata.builder()
                .traceId(MDC.get(AndesApiConstants.MDC_CORRELATION_ID))
                .correlationId(MDC.get(AndesApiConstants.MDC_CORRELATION_ID))
                .requestId(MDC.get(AndesApiConstants.MDC_REQUEST_ID))
                .build();
        return ApiResponse.success(body, metadata);
    }
}
