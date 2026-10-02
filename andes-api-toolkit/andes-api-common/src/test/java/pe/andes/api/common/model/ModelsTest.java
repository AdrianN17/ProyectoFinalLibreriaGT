package pe.andes.api.common.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelsTest {

    @Test
    void paginationOfComputesPagesAndFlags() {
        Pagination p = Pagination.of(0, 10, 25);
        assertEquals(3, p.getTotalPages());
        assertTrue(p.isFirst());
        assertFalse(p.isLast());
        assertTrue(Pagination.of(2, 10, 25).isLast());
        assertEquals(0, Pagination.of(0, 0, 5).getTotalPages());
        assertEquals(10, p.getSize());
        assertEquals(25, p.getTotalElements());
        assertEquals(0, p.getPage());
    }

    @Test
    void paginationEqualsHashCodeToString() {
        Pagination a = Pagination.of(1, 5, 20);
        Pagination b = Pagination.of(1, 5, 20);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, Pagination.of(2, 5, 20));
        assertNotEquals(a, "x");
        assertEquals(a, a);
        assertTrue(a.toString().contains("totalElements=20"));
    }

    @Test
    void apiErrorBuilderDefaultsAndEquality() {
        List<ApiErrorDetail> details = new ArrayList<>(List.of(ApiErrorDetail.of("f", "m")));
        Instant now = Instant.now();
        ApiError e = ApiError.builder().code("C").message("M").httpStatus(400).traceId("t")
                .timestamp(now).details(details).build();
        details.clear();
        assertEquals(1, e.getDetails().size());
        assertEquals("C", e.getCode());
        assertEquals("M", e.getMessage());
        assertEquals(400, e.getHttpStatus());
        assertEquals("t", e.getTraceId());
        assertEquals(now, e.getTimestamp());

        ApiError bare = ApiError.builder().code("C").build();
        assertNotNull(bare.getTimestamp());
        assertTrue(bare.getDetails().isEmpty());

        ApiError same = ApiError.builder().code("C").message("M").httpStatus(400).traceId("t")
                .details(List.of(ApiErrorDetail.of("f", "m"))).build();
        assertEquals(e, same);
        assertEquals(e.hashCode(), same.hashCode());
        assertNotEquals(e, bare);
        assertNotEquals(e, null);
        assertEquals(e, e);
        assertTrue(e.toString().contains("code='C'"));
    }

    @Test
    void apiErrorDetailFactoriesAndEquality() {
        ApiErrorDetail d = ApiErrorDetail.of("f", "c", "m");
        assertEquals("f", d.getField());
        assertEquals("c", d.getCode());
        assertEquals("m", d.getMessage());
        assertNull(d.getRejectedValue());
        assertNull(ApiErrorDetail.of("f", "m").getCode());
        assertEquals("m", ApiErrorDetail.of("f", "m").getMessage());
        ApiErrorDetail r = new ApiErrorDetail("f", "c", "m", 5);
        assertEquals(5, r.getRejectedValue());
        assertEquals(d, ApiErrorDetail.of("f", "c", "m"));
        assertEquals(d.hashCode(), ApiErrorDetail.of("f", "c", "m").hashCode());
        assertNotEquals(d, r);
        assertNotEquals(d, "x");
        assertTrue(r.toString().contains("rejectedValue=5"));
    }

    @Test
    void apiMetadataBuilderEqualityAndDefaults() {
        Instant ts = Instant.parse("2024-01-01T00:00:00Z");
        ApiMetadata m = ApiMetadata.builder().traceId("t").correlationId("c").requestId("r")
                .apiVersion("v2").timestamp(ts).build();
        assertEquals("t", m.getTraceId());
        assertEquals("c", m.getCorrelationId());
        assertEquals("r", m.getRequestId());
        assertEquals("v2", m.getApiVersion());
        assertEquals(ts, m.getTimestamp());
        ApiMetadata same = ApiMetadata.builder().traceId("t").correlationId("c").requestId("r")
                .apiVersion("v2").timestamp(ts).build();
        assertEquals(m, same);
        assertEquals(m.hashCode(), same.hashCode());
        assertNotEquals(m, ApiMetadata.builder().traceId("x").build());
        assertNotEquals(m, "x");
        assertNotNull(ApiMetadata.builder().build().getTimestamp());
        assertTrue(m.toString().contains("traceId"));
    }

    @Test
    void apiRequestMetadataBuilderAndEquality() {
        Instant ts = Instant.parse("2024-01-01T00:00:00Z");
        ApiRequestMetadata m = ApiRequestMetadata.builder().correlationId("c").requestId("r")
                .apiVersion("v1").receivedAt(ts).build();
        assertEquals("c", m.getCorrelationId());
        assertEquals("r", m.getRequestId());
        assertEquals("v1", m.getApiVersion());
        assertEquals(ts, m.getReceivedAt());
        ApiRequestMetadata same = ApiRequestMetadata.builder().correlationId("c").requestId("r")
                .apiVersion("v1").receivedAt(ts).build();
        assertEquals(m, same);
        assertEquals(m.hashCode(), same.hashCode());
        assertNotEquals(m, ApiRequestMetadata.builder().build());
        assertNotEquals(m, "x");
        assertNotNull(ApiRequestMetadata.builder().build().getReceivedAt());
        assertFalse(m.toString().isEmpty());
    }

    @Test
    void apiResponseSuccessErrorAndEquality() {
        ApiResponse<String> ok = ApiResponse.success("d");
        assertTrue(ok.isSuccess());
        assertEquals("d", ok.getData());
        assertNull(ok.getError());

        ApiError err = ApiError.builder().code("E").build();
        ApiResponse<String> ko = ApiResponse.error(err);
        assertFalse(ko.isSuccess());
        assertNull(ko.getData());
        assertSame(err, ko.getError());

        assertEquals(ok, ok);
        assertNotEquals(ok, ko);
        assertNotEquals(ok, "x");
        assertEquals(ko.hashCode(), ApiResponse.<String>error(err, ko.getMetadata()).hashCode());
        assertFalse(ok.toString().isEmpty());
    }
}
