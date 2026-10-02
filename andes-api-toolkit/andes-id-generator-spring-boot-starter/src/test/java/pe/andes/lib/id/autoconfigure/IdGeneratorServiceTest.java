package pe.andes.lib.id.autoconfigure;

import org.junit.jupiter.api.Test;
import pe.andes.lib.id.ChecksumUtils;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorServiceTest {

    private final IdGeneratorService service = new IdGeneratorService();

    @Test
    void newIdProducesUniqueUlids() {
        String a = service.newId();
        assertEquals(26, a.length());
        assertNotEquals(a, service.newId());
    }

    @Test
    void newIdWithPrefixStartsWithPrefix() {
        assertTrue(service.newId("ORD").startsWith("ORD"));
    }

    @Test
    void checksumDelegatesToSha256() {
        assertEquals(ChecksumUtils.sha256Hex("abc"), service.checksum("abc"));
        assertEquals(64, service.checksum("abc").length());
    }
}
