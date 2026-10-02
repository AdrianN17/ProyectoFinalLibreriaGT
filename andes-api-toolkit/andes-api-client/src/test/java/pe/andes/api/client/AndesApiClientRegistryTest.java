package pe.andes.api.client;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AndesApiClientRegistryTest {

    @Test
    void returnsRegisteredClientAndFailsForUnknown() {
        AndesApiClient client = new AndesApiClient("a", RestClient.create());
        AndesApiClientRegistry registry = new AndesApiClientRegistry(Map.of("a", client));

        assertSame(client, registry.get("a"));
        assertEquals("a", client.getName());
        assertNotNull(client.raw());
        assertEquals(1, registry.getAll().size());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> registry.get("zzz"));
        assertTrue(ex.getMessage().contains("zzz"));
    }

    @Test
    void registryIsImmutableSnapshot() {
        Map<String, AndesApiClient> source = new HashMap<>();
        source.put("a", new AndesApiClient("a", RestClient.create()));
        AndesApiClientRegistry registry = new AndesApiClientRegistry(source);
        source.clear();
        assertEquals(1, registry.getAll().size());
        assertThrows(UnsupportedOperationException.class, () -> registry.getAll().clear());
    }
}
