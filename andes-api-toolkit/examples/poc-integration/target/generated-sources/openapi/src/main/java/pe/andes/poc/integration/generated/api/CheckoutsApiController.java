package pe.andes.poc.integration.generated.api;

import pe.andes.poc.integration.generated.model.CheckoutEnvelope;
import pe.andes.poc.integration.generated.model.CheckoutRequest;
import pe.andes.poc.integration.generated.model.ErrorEnvelope;


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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:57.686528865-05:00[America/Lima]", comments = "Generator version: 7.11.0")
@Controller
@RequestMapping("${openapi.andesIntegrationPoCCheckout.base-path:}")
public class CheckoutsApiController implements CheckoutsApi {

    private final CheckoutsApiDelegate delegate;

    public CheckoutsApiController(@Autowired(required = false) CheckoutsApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new CheckoutsApiDelegate() {});
    }

    @Override
    public CheckoutsApiDelegate getDelegate() {
        return delegate;
    }

}
