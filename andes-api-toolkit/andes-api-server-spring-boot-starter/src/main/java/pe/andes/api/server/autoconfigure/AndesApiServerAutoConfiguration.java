package pe.andes.api.server.autoconfigure;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import pe.andes.api.server.config.AndesServerProperties;
import pe.andes.api.server.error.AndesExceptionMapper;
import pe.andes.api.server.error.GlobalExceptionHandler;
import pe.andes.api.server.openapi.AndesOpenApiFactory;
import pe.andes.api.server.web.AndesResponseBodyAdvice;
import pe.andes.api.server.web.CorrelationIdFilter;

import java.util.List;

/**
 * Autoconfiguración principal del starter de servidor Andes para aplicaciones Spring Boot.
 *
 * <p>Cuando la aplicación es web servlet, esta clase registra de forma condicional los
 * componentes transversales del toolkit: filtro de correlación, manejo global de excepciones,
 * {@code ResponseBodyAdvice} para respuestas uniformes y un bean {@code OpenAPI} complementario.
 * Cada bean puede deshabilitarse por propiedades y cede el control si la aplicación ya aporta una
 * implementación propia compatible.
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(AndesServerProperties.class)
public class AndesApiServerAutoConfiguration {

    /**
     * Registra el filtro servlet responsable de propagar {@code correlationId} y
     * {@code requestId}.
     *
     * <p>Se publica con la máxima precedencia para que el {@link org.slf4j.MDC} esté poblado
     * antes de que otros filtros o controladores generen logs.
     *
     * @param properties propiedades enlazadas del módulo servidor
     * @return registro del filtro Andes aplicable a todas las rutas
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "andes.api.server.correlation", name = "enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<CorrelationIdFilter> andesCorrelationIdFilter(AndesServerProperties properties) {
        CorrelationIdFilter filter = new CorrelationIdFilter(properties.getCorrelation().isGenerateIfMissing());
        FilterRegistrationBean<CorrelationIdFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * Registra el manejador global de excepciones del toolkit.
     *
     * @param exceptionMappers beans opcionales que adaptan excepciones externas al contrato Andes
     * @param properties propiedades enlazadas del módulo servidor
     * @return consejo global que serializa errores en {@code ApiResponse}
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "andes.api.server.error-handling", name = "enabled", havingValue = "true", matchIfMissing = true)
    public GlobalExceptionHandler andesGlobalExceptionHandler(List<AndesExceptionMapper<?>> exceptionMappers,
                                                                AndesServerProperties properties) {
        return new GlobalExceptionHandler(exceptionMappers, properties.getErrorHandling().isIncludeStackTrace());
    }

    /**
     * Registra el adaptador que envuelve respuestas exitosas de los controladores REST.
     *
     * @return implementación de {@code ResponseBodyAdvice} del toolkit
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "andes.api.server.response", name = "wrap-enabled", havingValue = "true", matchIfMissing = true)
    public AndesResponseBodyAdvice andesResponseBodyAdvice() {
        return new AndesResponseBodyAdvice();
    }

    /**
     * Registra un bean {@link OpenAPI} con metadatos adicionales para springdoc-openapi.
     *
     * @param properties propiedades enlazadas del módulo servidor
     * @return modelo OpenAPI enriquecido con la configuración Andes
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(OpenAPI.class)
    @ConditionalOnProperty(prefix = "andes.api.server.openapi", name = "enabled", havingValue = "true", matchIfMissing = true)
    public OpenAPI andesOpenApi(AndesServerProperties properties) {
        return AndesOpenApiFactory.build(properties.getOpenapi());
    }
}
