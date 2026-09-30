package pe.andes.poc.client.generated.inventory.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * InventoryFaultFault
 */

@JsonTypeName("InventoryFault_fault")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:54.929826446-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class InventoryFaultFault {

  private @Nullable String reason;

  private @Nullable String detail;

  public InventoryFaultFault reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
   */
  
  @JsonProperty("reason")
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public InventoryFaultFault detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Get detail
   * @return detail
   */
  
  @JsonProperty("detail")
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InventoryFaultFault inventoryFaultFault = (InventoryFaultFault) o;
    return Objects.equals(this.reason, inventoryFaultFault.reason) &&
        Objects.equals(this.detail, inventoryFaultFault.detail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reason, detail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InventoryFaultFault {\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
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

