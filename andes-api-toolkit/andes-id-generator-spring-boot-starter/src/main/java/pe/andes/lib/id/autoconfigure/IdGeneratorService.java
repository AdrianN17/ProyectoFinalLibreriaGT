package pe.andes.lib.id.autoconfigure;

import pe.andes.lib.id.ChecksumUtils;
import pe.andes.lib.id.UlidGenerator;

/**
 * Bean de Spring que ENVUELVE los metodos estaticos de la libreria clasica
 * {@code andes-id-generator} (Java puro, sin Spring), exponiendolos como una API orientada a
 * objetos e inyectable (por constructor, {@code @Autowired}, etc.) dentro de cualquier
 * aplicacion Spring/Spring Boot.
 *
 * <p>Este es el patron de "libreria de envoltura": la logica real vive en
 * {@link UlidGenerator}/{@link ChecksumUtils} (metodos estaticos, sin dependencias), y esta
 * clase solo agrega la capa de integracion con el ecosistema Spring (inyeccion de
 * dependencias, posibilidad de mockear en tests, extensibilidad futura vía interfaz, etc.),
 * sin modificar ni duplicar el codigo original.</p>
 */
public class IdGeneratorService {

    /**
     * Genera un identificador tipo ULID (26 caracteres, ordenable por tiempo).
     *
     * @return nuevo identificador unico
     */
    public String newId() {
        return UlidGenerator.generate();
    }

    /**
     * Genera un identificador tipo ULID con un prefijo legible, por ejemplo {@code "ORD-..."}.
     *
     * @param prefix prefijo de la entidad (por ejemplo {@code "ORD"}, {@code "CUS"})
     * @return nuevo identificador prefijado
     */
    public String newId(String prefix) {
        return UlidGenerator.withPrefix(prefix);
    }

    /**
     * Calcula un checksum SHA-256 (hexadecimal) del valor recibido, util como clave de
     * idempotencia o huella de auditoria.
     *
     * @param value valor de entrada
     * @return checksum SHA-256 en hexadecimal
     */
    public String checksum(String value) {
        return ChecksumUtils.sha256Hex(value);
    }
}
