package pe.andes.poc.client.generated.inventory.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import pe.andes.poc.client.generated.inventory.model.InventoryFaultFault;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * InventoryFault
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:54.929826446-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class InventoryFault {

  private @Nullable InventoryFaultFault fault;

  public InventoryFault fault(InventoryFaultFault fault) {
    this.fault = fault;
    return this;
  }

  /**
   * Get fault
   * @return fault
   */
  
  @JsonProperty("fault")
  public InventoryFaultFault getFault() {
    return fault;
  }

  public void setFault(InventoryFaultFault fault) {
    this.fault = fault;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InventoryFault inventoryFault = (InventoryFault) o;
    return Objects.equals(this.fault, inventoryFault.fault);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fault);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InventoryFault {\n");
    sb.append("    fault: ").append(toIndentedString(fault)).append("\n");
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

