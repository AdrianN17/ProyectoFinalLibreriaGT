package pe.andes.lib.id;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UlidGeneratorTest {

    @Test
    void generatesTwentySixCharacterIds() {
        String id = UlidGenerator.generate();
        assertEquals(26, id.length());
    }

    @Test
    void generatedIdsAreUnique() {
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            assertTrue(ids.add(UlidGenerator.generate()), "duplicate ULID generated");
        }
    }

    @Test
    void idsGeneratedLaterSortAfterEarlierOnes() throws InterruptedException {
        String first = UlidGenerator.generate();
        Thread.sleep(5);
        String second = UlidGenerator.generate();
        assertTrue(first.compareTo(second) < 0, "later ULID should sort after earlier one");
    }

    @Test
    void withPrefixPrependsPrefixAndDash() {
        String id = UlidGenerator.withPrefix("ORD");
        assertTrue(id.startsWith("ORD-"));
        assertEquals(26 + "ORD-".length(), id.length());
    }

    @Test
    void withPrefixOmitsDashWhenPrefixBlank() {
        String id = UlidGenerator.withPrefix(null);
        assertEquals(26, id.length());
    }
}
