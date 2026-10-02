package pe.andes.api.common.http;

import org.junit.jupiter.api.Test;
import pe.andes.api.common.util.ApiResponseUtils;
import pe.andes.api.common.util.OpenApiUtils;
import pe.andes.api.common.model.ApiError;
import pe.andes.api.common.model.ApiMetadata;
import pe.andes.api.common.model.ApiResponse;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void headerAndContentTypeConstantsMatchTheContract() {
        assertEquals("X-Correlation-Id", AndesHeaders.CORRELATION_ID);
        assertEquals("X-Request-Id", AndesHeaders.REQUEST_ID);
        assertEquals("X-Api-Version", AndesHeaders.API_VERSION);
        assertEquals("X-Trace-Id", AndesHeaders.TRACE_ID);
        assertEquals("Content-Type", AndesHeaders.CONTENT_TYPE);
        assertEquals("Accept", AndesHeaders.ACCEPT);
        assertEquals("Authorization", AndesHeaders.AUTHORIZATION);
        assertEquals("application/json", AndesContentTypes.APPLICATION_JSON);
        assertEquals("application/problem+json", AndesContentTypes.APPLICATION_PROBLEM_JSON);
        assertEquals("application/xml", AndesContentTypes.APPLICATION_XML);
        assertEquals("text/plain", AndesContentTypes.TEXT_PLAIN);
        assertEquals("andes.api", AndesApiConstants.CONFIG_PREFIX);
        assertEquals("correlationId", AndesApiConstants.MDC_CORRELATION_ID);
        assertEquals("requestId", AndesApiConstants.MDC_REQUEST_ID);
        assertEquals("traceId", AndesApiConstants.MDC_TRACE_ID);
        assertEquals("v1", AndesApiConstants.DEFAULT_API_VERSION);
    }

    @Test
    void httpStatusConstants() {
        assertEquals(200, AndesHttpStatus.OK);
        assertEquals(201, AndesHttpStatus.CREATED);
        assertEquals(204, AndesHttpStatus.NO_CONTENT);
        assertEquals(400, AndesHttpStatus.BAD_REQUEST);
        assertEquals(401, AndesHttpStatus.UNAUTHORIZED);
        assertEquals(403, AndesHttpStatus.FORBIDDEN);
        assertEquals(404, AndesHttpStatus.NOT_FOUND);
        assertEquals(409, AndesHttpStatus.CONFLICT);
        assertEquals(422, AndesHttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(500, AndesHttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(502, AndesHttpStatus.BAD_GATEWAY);
        assertEquals(503, AndesHttpStatus.SERVICE_UNAVAILABLE);
        assertEquals(504, AndesHttpStatus.GATEWAY_TIMEOUT);
    }

    @Test
    void apiResponseUtilsBuildsSuccessAndFailure() {
        ApiMetadata md = ApiMetadata.builder().traceId("t").build();
        ApiError err = ApiError.builder().code("X").httpStatus(400).build();

        assertTrue(ApiResponseUtils.ok("a").isSuccess());
        assertEquals("t", ApiResponseUtils.ok("a", md).getMetadata().getTraceId());
        ApiResponse<Object> f = ApiResponseUtils.fail(err);
        assertFalse(f.isSuccess());
        assertSame(err, f.getError());
        assertSame(md, ApiResponseUtils.fail(err, md).getMetadata());
        assertTrue(ApiResponseUtils.isSuccessful(ApiResponseUtils.ok(1)));
        assertFalse(ApiResponseUtils.isSuccessful(f));
        assertFalse(ApiResponseUtils.isSuccessful(null));
    }

    @Test
    void openApiUtilsValidations() {
        assertTrue(OpenApiUtils.isValidOperationId("getCustomer1"));
        assertFalse(OpenApiUtils.isValidOperationId("1get"));
        assertFalse(OpenApiUtils.isValidOperationId("get-customer"));
        assertFalse(OpenApiUtils.isValidOperationId(null));
        assertTrue(OpenApiUtils.isValidSemanticVersion("1.2.3"));
        assertTrue(OpenApiUtils.isValidSemanticVersion("1.2.3-rc.1+build.5"));
        assertFalse(OpenApiUtils.isValidSemanticVersion("1.2"));
        assertFalse(OpenApiUtils.isValidSemanticVersion(null));
        assertEquals("v2", OpenApiUtils.normalizeApiVersion(" v2 ", "v1"));
        assertEquals("v1", OpenApiUtils.normalizeApiVersion("  ", "v1"));
        assertEquals("v1", OpenApiUtils.normalizeApiVersion(null, "v1"));
    }
}
