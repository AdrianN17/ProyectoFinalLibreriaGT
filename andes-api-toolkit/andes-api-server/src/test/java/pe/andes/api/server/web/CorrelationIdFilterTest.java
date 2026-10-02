package pe.andes.api.server.web;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import pe.andes.api.common.http.AndesApiConstants;
import pe.andes.api.common.http.AndesHeaders;
import pe.andes.api.common.util.HeaderUtils;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class CorrelationIdFilterTest {

    private final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/x");
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    void generatesIdsWhenMissingAndExposesThemInMdcDuringTheChain() throws Exception {
        AtomicReference<String> inChain = new AtomicReference<>();
        new CorrelationIdFilter(true).doFilter(request, response, (req, res) ->
                inChain.set(MDC.get(AndesApiConstants.MDC_CORRELATION_ID)));

        String header = response.getHeader(AndesHeaders.CORRELATION_ID);
        assertNotNull(header);
        assertTrue(HeaderUtils.isValidCorrelationId(header));
        assertEquals(header, inChain.get());
        assertNotNull(response.getHeader(AndesHeaders.REQUEST_ID));
    }

    @Test
    void keepsValidIncomingIdsAndClearsMdcAfterwards() throws Exception {
        String correlation = HeaderUtils.generateCorrelationId();
        String requestId = HeaderUtils.generateRequestId();
        request.addHeader(AndesHeaders.CORRELATION_ID, correlation);
        request.addHeader(AndesHeaders.REQUEST_ID, requestId);

        new CorrelationIdFilter(true).doFilter(request, response, new MockFilterChain());

        assertEquals(correlation, response.getHeader(AndesHeaders.CORRELATION_ID));
        assertEquals(requestId, response.getHeader(AndesHeaders.REQUEST_ID));
        assertNull(MDC.get(AndesApiConstants.MDC_CORRELATION_ID));
        assertNull(MDC.get(AndesApiConstants.MDC_REQUEST_ID));
    }

    @Test
    void doesNotGenerateIdsWhenDisabled() throws Exception {
        new CorrelationIdFilter(false).doFilter(request, response, new MockFilterChain());
        assertNull(response.getHeader(AndesHeaders.CORRELATION_ID));
        assertNull(response.getHeader(AndesHeaders.REQUEST_ID));
    }

    @Test
    void clearsMdcEvenWhenChainFails() {
        assertThrows(IllegalStateException.class, () ->
                new CorrelationIdFilter(true).doFilter(request, response, (req, res) -> {
                    throw new IllegalStateException("boom");
                }));
        assertNull(MDC.get(AndesApiConstants.MDC_CORRELATION_ID));
    }
}
