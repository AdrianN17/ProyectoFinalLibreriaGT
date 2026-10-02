package pe.andes.api.client.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.HttpRequest;
import pe.andes.api.client.config.AndesClientProperties;
import pe.andes.api.common.http.AndesApiConstants;
import pe.andes.api.common.http.AndesHeaders;
import pe.andes.api.common.util.HeaderUtils;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AndesClientHeaderInterceptorTest {

    private final AndesClientProperties.ClientConfig config = new AndesClientProperties.ClientConfig();
    private final HttpHeaders headers = new HttpHeaders();
    private final HttpRequest request = mock(HttpRequest.class);
    private final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
    private final ClientHttpResponse response = mock(ClientHttpResponse.class);

    AndesClientHeaderInterceptorTest() throws Exception {
        when(request.getHeaders()).thenReturn(headers);
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        when(request.getURI()).thenReturn(URI.create("http://localhost/x"));
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(execution.execute(request, new byte[0])).thenReturn(response);
    }

    @AfterEach
    void cleanMdc() {
        MDC.clear();
    }

    private ClientHttpResponse run() throws Exception {
        return new AndesClientHeaderInterceptor(config).intercept(request, new byte[0], execution);
    }

    @Test
    void addsGeneratedIdsContentTypeAndDefaultHeaders() throws Exception {
        config.setDefaultHeaders(Map.of("X-Custom", "v"));
        assertSame(response, run());

        assertTrue(HeaderUtils.isValidCorrelationId(headers.getFirst(AndesHeaders.CORRELATION_ID)));
        assertNotNull(headers.getFirst(AndesHeaders.REQUEST_ID));
        assertEquals("application/json", headers.getFirst(AndesHeaders.CONTENT_TYPE));
        assertEquals("v", headers.getFirst("X-Custom"));
    }

    @Test
    void propagatesCorrelationIdFromMdc() throws Exception {
        String id = HeaderUtils.generateCorrelationId();
        MDC.put(AndesApiConstants.MDC_CORRELATION_ID, id);
        run();
        assertEquals(id, headers.getFirst(AndesHeaders.CORRELATION_ID));
    }

    @Test
    void doesNotOverrideExistingHeaders() throws Exception {
        headers.add(AndesHeaders.CORRELATION_ID, "mine");
        headers.add(AndesHeaders.REQUEST_ID, "mine-r");
        headers.add(AndesHeaders.CONTENT_TYPE, "text/plain");
        headers.add("X-Custom", "orig");
        config.setDefaultHeaders(Map.of("X-Custom", "other"));
        run();
        assertEquals("mine", headers.getFirst(AndesHeaders.CORRELATION_ID));
        assertEquals("mine-r", headers.getFirst(AndesHeaders.REQUEST_ID));
        assertEquals("text/plain", headers.getFirst(AndesHeaders.CONTENT_TYPE));
        assertEquals("orig", headers.getFirst("X-Custom"));
    }

    @Test
    void respectsDisabledFlags() throws Exception {
        config.setCorrelationIdEnabled(false);
        config.setRequestIdEnabled(false);
        run();
        assertNull(headers.getFirst(AndesHeaders.CORRELATION_ID));
        assertNull(headers.getFirst(AndesHeaders.REQUEST_ID));
    }
}
