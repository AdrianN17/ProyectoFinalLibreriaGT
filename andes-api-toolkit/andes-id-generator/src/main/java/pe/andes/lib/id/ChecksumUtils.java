package pe.andes.lib.id;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * Utilidades de checksum sobre cadenas de texto, utiles para validar integridad de payloads,
 * generar claves de idempotencia o huellas cortas de auditoria.
 *
 * <p>Clase clasica de Java puro: {@code final}, sin dependencias externas, con constructor
 * privado y metodos exclusivamente {@code static}.</p>
 */
public final class ChecksumUtils {

    private ChecksumUtils() {
        throw new AssertionError("ChecksumUtils is a static utility class and must not be instantiated");
    }

    /**
     * Calcula el CRC32 de una cadena UTF-8.
     *
     * @param input texto de entrada
     * @return valor CRC32 (0 si {@code input} es {@code null} o vacio)
     */
    public static long crc32(String input) {
        if (input == null || input.isEmpty()) {
            return 0L;
        }
        CRC32 crc32 = new CRC32();
        crc32.update(input.getBytes(StandardCharsets.UTF_8));
        return crc32.getValue();
    }

    /**
     * Calcula el hash SHA-256 de una cadena UTF-8 y lo devuelve en hexadecimal minusculas.
     *
     * @param input texto de entrada
     * @return hash SHA-256 en hexadecimal, o cadena vacia si {@code input} es {@code null}
     */
    public static String sha256Hex(String input) {
        if (input == null) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is guaranteed to be available on every standard JVM implementation.
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
