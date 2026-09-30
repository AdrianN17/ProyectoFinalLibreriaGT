package pe.andes.lib.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlugUtilsTest {

    @Test
    void slugifiesAccentedTextWithSymbols() {
        assertEquals("ordenes-de-compra-42", SlugUtils.slugify("Órdenes de Compra #42"));
    }

    @Test
    void slugifyReturnsEmptyForNullOrBlank() {
        assertEquals("", SlugUtils.slugify(null));
        assertEquals("", SlugUtils.slugify("   "));
    }

    @Test
    void truncateAddsSuffixWhenTooLong() {
        assertEquals("Hola mu...", SlugUtils.truncate("Hola mundo desde Andes", 10, "..."));
    }

    @Test
    void truncateReturnsOriginalWhenShortEnough() {
        assertEquals("Hola", SlugUtils.truncate("Hola", 10, "..."));
    }

    @Test
    void cannotBeInstantiated() {
        assertThrows(Exception.class, () -> {
            var constructor = SlugUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }
}
