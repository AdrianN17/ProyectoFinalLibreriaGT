package pe.andes.lib.id.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * AutoConfiguration que registra un {@link IdGeneratorService} listo para inyectar en
 * cualquier aplicacion Spring Boot que tenga este starter en el classpath (cero configuracion
 * manual requerida, "convention over configuration").
 *
 * <p>Registrada via {@code META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports}
 * (mecanismo estandar de Spring Boot 3+/4, reemplazo de {@code spring.factories}).</p>
 */
@AutoConfiguration
public class AndesIdGeneratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IdGeneratorService idGeneratorService() {
        return new IdGeneratorService();
    }
}
