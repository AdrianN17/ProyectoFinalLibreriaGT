package pe.andes.poc.client.generated.orders.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import pe.andes.poc.client.generated.orders.model.OrderItem;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateOrderRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:54.754076244-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class CreateOrderRequest {

  private Long customerId;

  
  private List<OrderItem> items = new ArrayList<>();

  public CreateOrderRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateOrderRequest(Long customerId, List<OrderItem> items) {
    this.customerId = customerId;
    this.items = items;
  }

  public CreateOrderRequest customerId(Long customerId) {
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

  public CreateOrderRequest items(List<OrderItem> items) {
    this.items = items;
    return this;
  }

  public CreateOrderRequest addItemsItem(OrderItem itemsItem) {
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
  @NotNull
  @JsonProperty("items")
  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> items) {
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
    CreateOrderRequest createOrderRequest = (CreateOrderRequest) o;
    return Objects.equals(this.customerId, createOrderRequest.customerId) &&
        Objects.equals(this.items, createOrderRequest.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateOrderRequest {\n");
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

