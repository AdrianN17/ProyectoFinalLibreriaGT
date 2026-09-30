package pe.andes.poc.integration.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import pe.andes.poc.integration.generated.model.CheckoutItem;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CheckoutRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:57.686528865-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class CheckoutRequest {

  private Long customerId;

  @Valid
  private List<@Valid CheckoutItem> items = new ArrayList<>();

  public CheckoutRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CheckoutRequest(Long customerId, List<@Valid CheckoutItem> items) {
    this.customerId = customerId;
    this.items = items;
  }

  public CheckoutRequest customerId(Long customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
   */
  @NotNull 
  @JsonProperty("customerId")
  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public CheckoutRequest items(List<@Valid CheckoutItem> items) {
    this.items = items;
    return this;
  }

  public CheckoutRequest addItemsItem(CheckoutItem itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   */
  @NotNull @Valid @Size(min = 1) 
  @JsonProperty("items")
  public List<@Valid CheckoutItem> getItems() {
    return items;
  }

  public void setItems(List<@Valid CheckoutItem> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CheckoutRequest checkoutRequest = (CheckoutRequest) o;
    return Objects.equals(this.customerId, checkoutRequest.customerId) &&
        Objects.equals(this.items, checkoutRequest.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckoutRequest {\n");
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

