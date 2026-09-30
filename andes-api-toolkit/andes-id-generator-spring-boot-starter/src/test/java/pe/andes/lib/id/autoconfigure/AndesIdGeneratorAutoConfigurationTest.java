package pe.andes.lib.id.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class AndesIdGeneratorAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(AndesIdGeneratorAutoConfiguration.class));

    @Test
    void registersIdGeneratorServiceBean() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(IdGeneratorService.class);
            IdGeneratorService service = context.getBean(IdGeneratorService.class);
            assertThat(service.newId()).hasSize(26);
            assertThat(service.newId("ORD")).startsWith("ORD-");
            assertThat(service.checksum("andes")).hasSize(64);
        });
    }

    @Test
    void backsOffWhenUserProvidesOwnBean() {
        contextRunner.withUserConfiguration(CustomBeanConfig.class).run(context -> {
            assertThat(context).hasSingleBean(IdGeneratorService.class);
            assertThat(context.getBean(IdGeneratorService.class)).isInstanceOf(CustomBeanConfig.CustomIdGeneratorService.class);
        });
    }

    static class CustomBeanConfig {
        @org.springframework.context.annotation.Bean
        IdGeneratorService idGeneratorService() {
            return new CustomIdGeneratorService();
        }

        static class CustomIdGeneratorService extends IdGeneratorService {
        }
    }
}
