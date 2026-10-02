package pe.andes.api.server.config;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AndesServerPropertiesTest {

    @Test
    void defaultsAreSafe() {
        AndesServerProperties p = new AndesServerProperties();
        assertTrue(p.getResponse().isWrapEnabled());
        assertTrue(p.getErrorHandling().isEnabled());
        assertFalse(p.getErrorHandling().isIncludeStackTrace());
        assertTrue(p.getCorrelation().isEnabled());
        assertTrue(p.getCorrelation().isGenerateIfMissing());
        assertTrue(p.getOpenapi().isEnabled());
        assertEquals("Andes API", p.getOpenapi().getTitle());
        assertEquals("1.0.0", p.getOpenapi().getVersion());
        assertEquals("", p.getOpenapi().getDescription());
        assertTrue(p.getOpenapi().getServers().isEmpty());
        assertTrue(p.getOpenapi().getTags().isEmpty());
        assertTrue(p.getOpenapi().getSecuritySchemes().isEmpty());
    }

    @Test
    void settersAreRoundTripped() {
        AndesServerProperties p = new AndesServerProperties();
        p.getResponse().setWrapEnabled(false);
        p.getErrorHandling().setEnabled(false);
        p.getErrorHandling().setIncludeStackTrace(true);
        p.getCorrelation().setEnabled(false);
        p.getCorrelation().setGenerateIfMissing(false);
        assertFalse(p.getResponse().isWrapEnabled());
        assertFalse(p.getErrorHandling().isEnabled());
        assertTrue(p.getErrorHandling().isIncludeStackTrace());
        assertFalse(p.getCorrelation().isEnabled());
        assertFalse(p.getCorrelation().isGenerateIfMissing());

        AndesServerProperties.OpenApi o = p.getOpenapi();
        o.setEnabled(false);
        o.setTitle("T");
        o.setDescription("D");
        o.setVersion("2.0.0");
        AndesServerProperties.Contact c = new AndesServerProperties.Contact();
        c.setName("n"); c.setEmail("e"); c.setUrl("u");
        o.setContact(c);
        AndesServerProperties.License l = new AndesServerProperties.License();
        l.setName("ln"); l.setUrl("lu");
        o.setLicense(l);
        AndesServerProperties.ServerInfo s = new AndesServerProperties.ServerInfo();
        s.setUrl("http://s"); s.setDescription("sd");
        o.setServers(List.of(s));
        AndesServerProperties.TagInfo t = new AndesServerProperties.TagInfo();
        t.setName("tn"); t.setDescription("td");
        o.setTags(List.of(t));
        AndesServerProperties.SecuritySchemeInfo ss = new AndesServerProperties.SecuritySchemeInfo();
        ss.setType("http"); ss.setScheme("bearer"); ss.setBearerFormat("JWT"); ss.setIn("header"); ss.setName("Authorization");
        o.setSecuritySchemes(Map.of("bearer", ss));

        assertFalse(o.isEnabled());
        assertEquals("T", o.getTitle());
        assertEquals("D", o.getDescription());
        assertEquals("2.0.0", o.getVersion());
        assertEquals("n", o.getContact().getName());
        assertEquals("e", o.getContact().getEmail());
        assertEquals("u", o.getContact().getUrl());
        assertEquals("ln", o.getLicense().getName());
        assertEquals("lu", o.getLicense().getUrl());
        assertEquals("http://s", o.getServers().get(0).getUrl());
        assertEquals("sd", o.getServers().get(0).getDescription());
        assertEquals("tn", o.getTags().get(0).getName());
        assertEquals("td", o.getTags().get(0).getDescription());
        SecuritySchemeAssert.check(o.getSecuritySchemes().get("bearer"));
    }

    private static final class SecuritySchemeAssert {
        static void check(AndesServerProperties.SecuritySchemeInfo s) {
            assertEquals("http", s.getType());
            assertEquals("bearer", s.getScheme());
            assertEquals("JWT", s.getBearerFormat());
            assertEquals("header", s.getIn());
            assertEquals("Authorization", s.getName());
        }
    }
}
