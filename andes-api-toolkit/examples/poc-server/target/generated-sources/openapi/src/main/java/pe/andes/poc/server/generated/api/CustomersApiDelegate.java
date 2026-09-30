package pe.andes.poc.server.generated.api;

import pe.andes.poc.server.generated.model.CustomerEnvelope;
import pe.andes.poc.server.generated.model.CustomerPageEnvelope;
import pe.andes.poc.server.generated.model.CustomerRequest;
import pe.andes.poc.server.generated.model.ErrorEnvelope;
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
 * A delegate to be called by the {@link CustomersApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:08:29.761036194-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public interface CustomersApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /api/v1/customers : Create a new customer
     *
     * @param customerRequest  (required)
     * @return Created customer (status code 201)
     *         or Validation error (status code 422)
     * @see CustomersApi#createCustomer
     */
    default ResponseEntity<CustomerEnvelope> createCustomer(CustomerRequest customerRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"fullName\" : \"fullName\", \"id\" : 0, \"email\" : \"email\" }, \"success\" : true, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 2, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : \"\", \"success\" : false, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 2, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /api/v1/customers/{id} : Delete a customer
     *
     * @param id  (required)
     * @return Deleted (status code 204)
     *         or Customer not found (status code 404)
     * @see CustomersApi#deleteCustomer
     */
    default ResponseEntity<Void> deleteCustomer(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : \"\", \"success\" : false, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 2, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/v1/customers/{id} : Get a customer by id
     *
     * @param id  (required)
     * @return Customer found (status code 200)
     *         or Customer not found (status code 404)
     * @see CustomersApi#getCustomerById
     */
    default ResponseEntity<CustomerEnvelope> getCustomerById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"fullName\" : \"fullName\", \"id\" : 0, \"email\" : \"email\" }, \"success\" : true, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 2, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : \"\", \"success\" : false, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 2, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/v1/customers : List customers (paginated)
     *
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return Page of customers (status code 200)
     * @see CustomersApi#listCustomers
     */
    default ResponseEntity<CustomerPageEnvelope> listCustomers(Integer page,
        Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"metadata\" : { \"traceId\" : \"traceId\", \"apiVersion\" : \"apiVersion\", \"requestId\" : \"requestId\", \"correlationId\" : \"correlationId\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, \"data\" : { \"pagination\" : { \"size\" : 1, \"last\" : true, \"totalPages\" : 5, \"page\" : 6, \"first\" : true, \"totalElements\" : 5 }, \"content\" : [ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"fullName\" : \"fullName\", \"id\" : 0, \"email\" : \"email\" }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"fullName\" : \"fullName\", \"id\" : 0, \"email\" : \"email\" } ] }, \"success\" : true, \"error\" : { \"traceId\" : \"traceId\", \"code\" : \"code\", \"httpStatus\" : 2, \"details\" : [ { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" }, { \"code\" : \"code\", \"field\" : \"field\", \"message\" : \"message\", \"rejectedValue\" : \"\" } ], \"message\" : \"message\", \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
