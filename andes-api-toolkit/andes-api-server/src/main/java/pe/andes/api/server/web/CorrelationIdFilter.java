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
 * Filtro servlet que resuelve, propaga y limpia los identificadores de trazabilidad por petición.
 *
 * <p>Al extender {@link OncePerRequestFilter}, Spring garantiza una sola ejecución por request.
 * El filtro lee los encabezados Andes de correlación, genera valores cuando está permitido,
 * publica ambos identificadores en {@link MDC} para enriquecer logs y los devuelve en la
 * respuesta HTTP. También emite un registro básico de entrada/salida para facilitar el
 * seguimiento extremo a extremo de las llamadas.
 */
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);

    private final boolean generateIfMissing;

    /**
     * Crea el filtro indicando si puede generar identificadores faltantes o inválidos.
     *
     * @param generateIfMissing {@code true} para generar {@code correlationId}/{@code requestId}
     *                          cuando el cliente no los provee correctamente
     */
    public CorrelationIdFilter(boolean generateIfMissing) {
        this.generateIfMissing = generateIfMissing;
    }

    /**
     * Ejecuta la lógica principal del filtro para una petición HTTP.
     *
     * <p>El método resuelve los identificadores, los registra en el {@link MDC}, los refleja en la
     * respuesta y delega la continuación de la cadena de filtros. Finalmente limpia el contexto
     * para evitar fugas entre peticiones reutilizando hilos del contenedor.
     *
     * @param request petición HTTP entrante
     * @param response respuesta HTTP saliente
     * @param filterChain cadena de filtros del contenedor servlet
     * @throws ServletException si ocurre un error propio de la cadena servlet
     * @throws IOException si falla la lectura o escritura del flujo HTTP
     */
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
