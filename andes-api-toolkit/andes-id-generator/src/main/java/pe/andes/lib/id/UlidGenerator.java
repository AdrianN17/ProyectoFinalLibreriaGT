package pe.andes.lib.id;

import java.security.SecureRandom;
import java.time.Clock;

/**
 * Generador de identificadores unicos, ordenables lexicograficamente por tiempo de creacion
 * (estilo ULID simplificado): 48 bits de timestamp en milisegundos + 80 bits de aleatoriedad,
 * codificados en Base32 Crockford (sin caracteres ambiguos como 0/O, 1/I/L).
 *
 * <p>Clase clasica de Java puro: {@code final}, sin dependencias externas, con constructor
 * privado y metodos exclusivamente {@code static}. Thread-safe.</p>
 */
public final class UlidGenerator {

    private static final char[] CROCKFORD_ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Clock CLOCK = Clock.systemUTC();

    private UlidGenerator() {
        throw new AssertionError("UlidGenerator is a static utility class and must not be instantiated");
    }

    /**
     * Genera un nuevo identificador de 26 caracteres, unico y ordenable por tiempo de creacion.
     *
     * @return identificador tipo ULID
     */
    public static String generate() {
        long timestamp = CLOCK.millis();
        byte[] randomBytes = new byte[10];
        RANDOM.nextBytes(randomBytes);
        return encodeTimestamp(timestamp) + encodeRandom(randomBytes);
    }

    /**
     * Genera un identificador con un prefijo legible, util para distinguir el tipo de entidad,
     * por ejemplo {@code withPrefix("ORD")} -> {@code "ORD-01J8Z3K7NPQR8S9T0V1W2X3Y4Z"}.
     *
     * @param prefix prefijo a anteponer (puede ser {@code null} o vacio, en cuyo caso se omite)
     * @return identificador prefijado
     */
    public static String withPrefix(String prefix) {
        String id = generate();
        return (prefix == null || prefix.isBlank()) ? id : prefix + "-" + id;
    }

    private static String encodeTimestamp(long timestamp) {
        char[] chars = new char[10];
        long value = timestamp;
        for (int i = 9; i >= 0; i--) {
            chars[i] = CROCKFORD_ALPHABET[(int) (value & 0x1F)];
            value >>>= 5;
        }
        return new String(chars);
    }

    private static String encodeRandom(byte[] randomBytes) {
        StringBuilder sb = new StringBuilder(16);
        int bitBuffer = 0;
        int bitsInBuffer = 0;
        for (byte b : randomBytes) {
            bitBuffer = (bitBuffer << 8) | (b & 0xFF);
            bitsInBuffer += 8;
            while (bitsInBuffer >= 5) {
                bitsInBuffer -= 5;
                sb.append(CROCKFORD_ALPHABET[(bitBuffer >>> bitsInBuffer) & 0x1F]);
            }
        }
        if (bitsInBuffer > 0) {
            sb.append(CROCKFORD_ALPHABET[(bitBuffer << (5 - bitsInBuffer)) & 0x1F]);
        }
        return sb.substring(0, 16);
    }
}
