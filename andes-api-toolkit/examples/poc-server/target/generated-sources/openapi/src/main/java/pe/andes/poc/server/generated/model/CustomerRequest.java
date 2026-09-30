package pe.andes.poc.server.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CustomerRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:08:29.761036194-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class CustomerRequest {

  private String fullName;

  private String email;

  public CustomerRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CustomerRequest(String fullName, String email) {
    this.fullName = fullName;
    this.email = email;
  }

  public CustomerRequest fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   */
  @NotNull @Size(min = 3) 
  @JsonProperty("fullName")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public CustomerRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @NotNull @jakarta.validation.constraints.Email 
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerRequest customerRequest = (CustomerRequest) o;
    return Objects.equals(this.fullName, customerRequest.fullName) &&
        Objects.equals(this.email, customerRequest.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fullName, email);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerRequest {\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

