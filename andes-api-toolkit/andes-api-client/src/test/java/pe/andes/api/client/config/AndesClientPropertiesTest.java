package pe.andes.api.client.config;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AndesClientPropertiesTest {

    @Test
    void clientConfigDefaults() {
        AndesClientProperties.ClientConfig c = new AndesClientProperties.ClientConfig();
        assertNull(c.getBaseUrl());
        assertEquals(Duration.ofSeconds(2), c.getConnectTimeout());
        assertEquals(Duration.ofSeconds(5), c.getReadTimeout());
        assertTrue(c.isCorrelationIdEnabled());
        assertTrue(c.isRequestIdEnabled());
        assertTrue(c.getDefaultHeaders().isEmpty());
        assertTrue(new AndesClientProperties().getClients().isEmpty());
    }

    @Test
    void settersAreRoundTripped() {
        AndesClientProperties.ClientConfig c = new AndesClientProperties.ClientConfig();
        c.setBaseUrl("http://x");
        c.setConnectTimeout(Duration.ofSeconds(1));
        c.setReadTimeout(Duration.ofSeconds(9));
        c.setCorrelationIdEnabled(false);
        c.setRequestIdEnabled(false);
        c.setDefaultHeaders(Map.of("A", "b"));
        AndesClientProperties p = new AndesClientProperties();
        p.setClients(Map.of("main", c));

        AndesClientProperties.ClientConfig got = p.getClients().get("main");
        assertEquals("http://x", got.getBaseUrl());
        assertEquals(Duration.ofSeconds(1), got.getConnectTimeout());
        assertEquals(Duration.ofSeconds(9), got.getReadTimeout());
        assertFalse(got.isCorrelationIdEnabled());
        assertFalse(got.isRequestIdEnabled());
        assertEquals("b", got.getDefaultHeaders().get("A"));
    }
}
