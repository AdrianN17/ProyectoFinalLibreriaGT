package pe.andes.lib.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaskUtilsTest {

    @Test
    void masksEmailKeepingFirstCharAndDomain() {
        assertEquals("a*****@andes.pe", MaskUtils.maskEmail("adrian@andes.pe"));
    }

    @Test
    void maskEmailReturnsInputWhenInvalid() {
        assertEquals("not-an-email", MaskUtils.maskEmail("not-an-email"));
    }

    @Test
    void masksDigitsKeepingLastVisibleOnes() {
        assertEquals("************1111", MaskUtils.maskDigits("4111111111111111", 4));
    }

    @Test
    void maskDigitsHandlesVisibleGreaterThanLength() {
        assertEquals("123", MaskUtils.maskDigits("123", 10));
    }
}
