package pe.andes.api.server.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.andes.api.common.http.AndesApiConstants;
import pe.andes.api.common.http.AndesHeaders;
import pe.andes.api.common.util.HeaderUtils;

import java.io.IOException;

/**
 * Reads (or generates) the correlation id and request id for every incoming request,
 * exposes them via {@link MDC} for logging and echoes them back in the response headers.
 * Also logs a single line per request (method, URI, status, duration) so every server
 * PoC gets basic request logging out of the box.
 */
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);

    private final boolean generateIfMissing;

    public CorrelationIdFilter(boolean generateIfMissing) {
        this.generateIfMissing = generateIfMissing;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String correlationId = resolveCorrelationId(request.getHeader(AndesHeaders.CORRELATION_ID));
        String requestId = resolveRequestId(request.getHeader(AndesHeaders.REQUEST_ID));

        MDC.put(AndesApiConstants.MDC_CORRELATION_ID, correlationId);
        MDC.put(AndesApiConstants.MDC_REQUEST_ID, requestId);
        long startedAt = System.currentTimeMillis();
        try {
            if (correlationId != null) {
                response.setHeader(AndesHeaders.CORRELATION_ID, correlationId);
            }
            if (requestId != null) {
                response.setHeader(AndesHeaders.REQUEST_ID, requestId);
            }
            log.info(">> {} {}", request.getMethod(), request.getRequestURI());
            filterChain.doFilter(request, response);
            log.info("<< {} {} -> {} ({} ms)", request.getMethod(), request.getRequestURI(),
                    response.getStatus(), System.currentTimeMillis() - startedAt);
        } finally {
            MDC.remove(AndesApiConstants.MDC_CORRELATION_ID);
            MDC.remove(AndesApiConstants.MDC_REQUEST_ID);
        }
    }

    private String resolveCorrelationId(String headerValue) {
        if (HeaderUtils.isValidCorrelationId(headerValue)) {
            return headerValue;
        }
        return generateIfMissing ? HeaderUtils.generateCorrelationId() : headerValue;
    }

    private String resolveRequestId(String headerValue) {
        if (HeaderUtils.isValidRequestId(headerValue)) {
            return headerValue;
        }
        return generateIfMissing ? HeaderUtils.generateRequestId() : headerValue;
    }
}
