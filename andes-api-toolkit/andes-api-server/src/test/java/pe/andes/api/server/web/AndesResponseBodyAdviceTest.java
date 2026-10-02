package pe.andes.api.server.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import pe.andes.api.common.http.AndesApiConstants;
import pe.andes.api.common.model.ApiResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AndesResponseBodyAdviceTest {

    private final AndesResponseBodyAdvice advice = new AndesResponseBodyAdvice();

    @AfterEach
    void cleanMdc() {
        MDC.clear();
    }

    private MethodParameter returning(Class<?> type) {
        MethodParameter p = mock(MethodParameter.class);
        when(p.getParameterType()).thenAnswer(i -> type);
        return p;
    }

    @Test
    void supportsEverythingExceptAlreadyWrappedResponses() {
        assertTrue(advice.supports(returning(String.class), null));
        assertFalse(advice.supports(returning(ApiResponse.class), null));
    }

    @Test
    @SuppressWarnings("unchecked")
    void wrapsBodyWithMdcMetadata() {
        MDC.put(AndesApiConstants.MDC_CORRELATION_ID, "corr");
        MDC.put(AndesApiConstants.MDC_REQUEST_ID, "req");

        Object result = advice.beforeBodyWrite("hello", returning(String.class), null, null, null, null);

        ApiResponse<String> response = (ApiResponse<String>) result;
        assertTrue(response.isSuccess());
        assertEquals("hello", response.getData());
        assertEquals("corr", response.getMetadata().getCorrelationId());
        assertEquals("corr", response.getMetadata().getTraceId());
        assertEquals("req", response.getMetadata().getRequestId());
    }

    @Test
    void doesNotDoubleWrap() {
        ApiResponse<String> existing = ApiResponse.success("x");
        assertSame(existing, advice.beforeBodyWrite(existing, returning(ApiResponse.class), null, null, null, null));
    }
}
