package pe.andes.poc.wrapperdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.andes.lib.id.autoconfigure.IdGeneratorService;

import java.util.Map;

/**
 * Controlador de demostracion: {@link IdGeneratorService} llega aqui ya inyectado
 * automaticamente por Spring gracias a {@code andes-id-generator-spring-boot-starter}
 * (ninguna configuracion manual necesaria: cero {@code @Bean}, cero {@code new}).
 */
@RestController
public class IdGeneratorDemoController {

    private final IdGeneratorService idGeneratorService;

    public IdGeneratorDemoController(IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @GetMapping("/api/v1/ids")
    public Map<String, String> generate(@RequestParam(required = false) String prefix) {
        String id = (prefix == null || prefix.isBlank())
                ? idGeneratorService.newId()
                : idGeneratorService.newId(prefix);
        return Map.of("id", id, "checksum", idGeneratorService.checksum(id));
    }

    @GetMapping("/api/v1/ids/{value}/checksum")
    public Map<String, String> checksum(@PathVariable String value) {
        return Map.of("value", value, "checksum", idGeneratorService.checksum(value));
    }
}
