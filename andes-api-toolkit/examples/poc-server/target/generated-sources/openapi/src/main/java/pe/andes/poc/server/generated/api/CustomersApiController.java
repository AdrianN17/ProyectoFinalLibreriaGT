package pe.andes.poc.server.generated.api;

import pe.andes.poc.server.generated.model.CustomerEnvelope;
import pe.andes.poc.server.generated.model.CustomerPageEnvelope;
import pe.andes.poc.server.generated.model.CustomerRequest;
import pe.andes.poc.server.generated.model.ErrorEnvelope;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:06:49.880981541-05:00[America/Lima]", comments = "Generator version: 7.11.0")
@Controller
@RequestMapping("${openapi.andesCustomer.base-path:}")
public class CustomersApiController implements CustomersApi {

    private final CustomersApiDelegate delegate;

    public CustomersApiController(@Autowired(required = false) CustomersApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new CustomersApiDelegate() {});
    }

    @Override
    public CustomersApiDelegate getDelegate() {
        return delegate;
    }

}
