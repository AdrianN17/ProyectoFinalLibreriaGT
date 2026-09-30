package pe.andes.poc.classiclibs;

import pe.andes.lib.id.ChecksumUtils;
import pe.andes.lib.id.UlidGenerator;
import pe.andes.lib.text.MaskUtils;
import pe.andes.lib.text.SlugUtils;

/**
 * PoC de "Java puro": NO usa Spring ni ningun framework. Solo un {@code main()} plano que
 * demuestra el uso directo de las dos librerias clasicas del toolkit:
 * <ul>
 *   <li>{@code andes-text-utils} ({@link SlugUtils}, {@link MaskUtils})</li>
 *   <li>{@code andes-id-generator} ({@link UlidGenerator}, {@link ChecksumUtils})</li>
 * </ul>
 * Ejecucion: {@code mvn -pl examples/poc-classic-libs -am package && java -jar examples/poc-classic-libs/target/poc-classic-libs.jar}
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        System.out.println("=== andes-text-utils ===");
        String title = "Órdenes de Compra #42 - Cliente Preferente";
        System.out.println("slugify(\"" + title + "\") = " + SlugUtils.slugify(title));
        System.out.println("truncate(..., 20, \"...\") = " + SlugUtils.truncate(title, 20, "..."));
        System.out.println("maskEmail(\"adrian@andes.pe\") = " + MaskUtils.maskEmail("adrian@andes.pe"));
        System.out.println("maskDigits(\"4111111111111111\", 4) = " + MaskUtils.maskDigits("4111111111111111", 4));

        System.out.println();
        System.out.println("=== andes-id-generator ===");
        String orderId = UlidGenerator.withPrefix("ORD");
        System.out.println("UlidGenerator.withPrefix(\"ORD\") = " + orderId);
        System.out.println("UlidGenerator.generate()        = " + UlidGenerator.generate());
        System.out.println("ChecksumUtils.crc32(orderId)    = " + ChecksumUtils.crc32(orderId));
        System.out.println("ChecksumUtils.sha256Hex(orderId)= " + ChecksumUtils.sha256Hex(orderId));
    }
}
