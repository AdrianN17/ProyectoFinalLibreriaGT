package pe.andes.poc.integration.generated.api;

import pe.andes.poc.integration.generated.model.CheckoutEnvelope;
import pe.andes.poc.integration.generated.model.CheckoutRequest;
import pe.andes.poc.integration.generated.model.ErrorEnvelope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link CheckoutsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:06:54.005441654-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public interface CheckoutsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/v1/checkouts : Place a checkout, which internally creates an order in the external Orders API
     *
     * @param checkoutRequest  (required)
     * @return Checkout created (status code 201)
     *         or Validation error (status code 422)
     * @see CheckoutsApi#createCheckout
     */
    default ResponseEntity<CheckoutEnvelope> createCheckout(CheckoutRequest checkoutRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : { \"totalAmount\" : 6.027456183070403, \"orderId\" : \"orderId\", \"customerId\" : 0, \"items\" : [ { \"unitPrice\" : 5.962133916683182, \"quantity\" : 1, \"sku\" : \"sku\" }, { \"unitPrice\" : 5.962133916683182, \"quantity\" : 1, \"sku\" : \"sku\" } ], \"status\" : \"status\" }, \"success\" : true, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 5, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : \"\", \"success\" : false, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 5, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/v1/checkouts/{orderId} : Retrieve a previously placed checkout by its remote order id
     *
     * @param orderId  (required)
     * @return Checkout found (status code 200)
     *         or Checkout not found (status code 404)
     * @see CheckoutsApi#getCheckout
     */
    default ResponseEntity<CheckoutEnvelope> getCheckout(String orderId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : { \"totalAmount\" : 6.027456183070403, \"orderId\" : \"orderId\", \"customerId\" : 0, \"items\" : [ { \"unitPrice\" : 5.962133916683182, \"quantity\" : 1, \"sku\" : \"sku\" }, { \"unitPrice\" : 5.962133916683182, \"quantity\" : 1, \"sku\" : \"sku\" } ], \"status\" : \"status\" }, \"success\" : true, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 5, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : \"\", \"success\" : false, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 5, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
