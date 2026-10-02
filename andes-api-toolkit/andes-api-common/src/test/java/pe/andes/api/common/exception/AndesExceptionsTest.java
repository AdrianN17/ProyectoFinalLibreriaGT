package pe.andes.api.common.exception;

import org.junit.jupiter.api.Test;
import pe.andes.api.common.model.ApiErrorDetail;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AndesExceptionsTest {

    @Test
    void eachExceptionExposesItsErrorCodeAndStatus() {
        assertEquals("AUTHENTICATION_ERROR", new AndesAuthenticationException("m").getErrorCode());
        assertEquals(401, new AndesAuthenticationException("m", new RuntimeException()).getHttpStatus());
        assertEquals("AUTHORIZATION_ERROR", new AndesAuthorizationException("m").getErrorCode());
        assertEquals(403, new AndesAuthorizationException("m", new RuntimeException()).getHttpStatus());
        assertEquals("BAD_REQUEST", new AndesBadRequestException("m").getErrorCode());
        assertEquals(400, new AndesBadRequestException("m", new RuntimeException()).getHttpStatus());
        assertEquals("CONFLICT", new AndesConflictException("m").getErrorCode());
        assertEquals(409, new AndesConflictException("m", new RuntimeException()).getHttpStatus());
        assertEquals("NOT_FOUND", new AndesNotFoundException("m").getErrorCode());
        assertEquals(404, new AndesNotFoundException("m", new RuntimeException()).getHttpStatus());
        assertEquals("VALIDATION_ERROR", new AndesValidationException("m").getErrorCode());
        assertEquals(422, new AndesValidationException("m", List.of()).getHttpStatus());
    }

    @Test
    void fullConstructorsKeepDetailsTraceIdAndCause() {
        Throwable cause = new RuntimeException("x");
        List<ApiErrorDetail> details = List.of(ApiErrorDetail.of("f", "bad"));

        AndesApiException[] all = {
                new AndesAuthenticationException("m", details, "t1", cause),
                new AndesAuthorizationException("m", details, "t1", cause),
                new AndesBadRequestException("m", details, "t1", cause),
                new AndesConflictException("m", details, "t1", cause),
                new AndesNotFoundException("m", details, "t1", cause),
                new AndesValidationException("m", details, "t1", cause)
        };
        for (AndesApiException e : all) {
            assertEquals("t1", e.getTraceId());
            assertEquals(details, e.getDetails());
            assertSame(cause, e.getCause());
            assertEquals("m", e.getMessage());
        }
    }

    @Test
    void detailsAreDefensivelyCopiedAndNeverNull() {
        List<ApiErrorDetail> mutable = new ArrayList<>(List.of(ApiErrorDetail.of("f", "bad")));
        AndesApiException e = new AndesValidationException("m", mutable);
        mutable.clear();
        assertEquals(1, e.getDetails().size());
        assertThrows(UnsupportedOperationException.class, () -> e.getDetails().clear());
        assertTrue(new AndesNotFoundException("m").getDetails().isEmpty());
        assertNull(new AndesNotFoundException("m").getTraceId());
    }

    @Test
    void remoteServiceExceptionCarriesEndpointAndRemoteStatus() {
        Throwable cause = new RuntimeException();
        AndesRemoteServiceException e = new AndesRemoteServiceException("down", "/orders", 503, cause);
        assertEquals("REMOTE_SERVICE_ERROR", e.getErrorCode());
        assertEquals(502, e.getHttpStatus());
        assertEquals("/orders", e.getEndpoint());
        assertEquals(503, e.getRemoteHttpStatus());
        assertSame(cause, e.getCause());

        AndesRemoteServiceException full = new AndesRemoteServiceException("down", "/o", 500,
                List.of(ApiErrorDetail.of("a", "b")), "tr", null);
        assertEquals("tr", full.getTraceId());
        assertEquals(1, full.getDetails().size());
    }
}
