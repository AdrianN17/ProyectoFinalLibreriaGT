package pe.andes.poc.server.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import pe.andes.api.common.model.ApiError;
import pe.andes.api.common.model.ApiMetadata;
import pe.andes.poc.server.generated.model.CustomerPage;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CustomerPageEnvelope
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:53.161529900-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class CustomerPageEnvelope {

  private @Nullable Boolean success;

  private @Nullable CustomerPage data;

  private @Nullable ApiError error;

  private @Nullable ApiMetadata metadata;

  public CustomerPageEnvelope success(Boolean success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
   */
  
  @JsonProperty("success")
  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public CustomerPageEnvelope data(CustomerPage data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
   */
  @Valid 
  @JsonProperty("data")
  public CustomerPage getData() {
    return data;
  }

  public void setData(CustomerPage data) {
    this.data = data;
  }

  public CustomerPageEnvelope error(ApiError error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
   */
  @Valid 
  @JsonProperty("error")
  public ApiError getError() {
    return error;
  }

  public void setError(ApiError error) {
    this.error = error;
  }

  public CustomerPageEnvelope metadata(ApiMetadata metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Get metadata
   * @return metadata
   */
  @Valid 
  @JsonProperty("metadata")
  public ApiMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(ApiMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerPageEnvelope customerPageEnvelope = (CustomerPageEnvelope) o;
    return Objects.equals(this.success, customerPageEnvelope.success) &&
        Objects.equals(this.data, customerPageEnvelope.data) &&
        Objects.equals(this.error, customerPageEnvelope.error) &&
        Objects.equals(this.metadata, customerPageEnvelope.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, data, error, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerPageEnvelope {\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

