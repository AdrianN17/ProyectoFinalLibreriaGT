package pe.andes.lib.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ChecksumUtilsTest {

    @Test
    void crc32IsDeterministic() {
        assertEquals(ChecksumUtils.crc32("andes"), ChecksumUtils.crc32("andes"));
    }

    @Test
    void crc32ChangesWithInput() {
        assertNotEquals(ChecksumUtils.crc32("andes"), ChecksumUtils.crc32("andes-api"));
    }

    @Test
    void sha256HexProducesSixtyFourHexChars() {
        String hash = ChecksumUtils.sha256Hex("andes-api-toolkit");
        assertEquals(64, hash.length());
        assertEquals(hash, ChecksumUtils.sha256Hex("andes-api-toolkit"));
    }

    @Test
    void sha256HexOfNullIsEmpty() {
        assertEquals("", ChecksumUtils.sha256Hex(null));
    }
}
