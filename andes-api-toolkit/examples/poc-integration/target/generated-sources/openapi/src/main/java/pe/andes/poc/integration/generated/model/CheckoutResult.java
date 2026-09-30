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
 * CheckoutResult
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:57.686528865-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class CheckoutResult {

  private @Nullable String orderId;

  private @Nullable Long customerId;

  private @Nullable Double totalAmount;

  private @Nullable String status;

  @Valid
  private List<@Valid CheckoutItem> items = new ArrayList<>();

  public CheckoutResult orderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
   */
  
  @JsonProperty("orderId")
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public CheckoutResult customerId(Long customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
   */
  
  @JsonProperty("customerId")
  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public CheckoutResult totalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  /**
   * Get totalAmount
   * @return totalAmount
   */
  
  @JsonProperty("totalAmount")
  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public CheckoutResult status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CheckoutResult items(List<@Valid CheckoutItem> items) {
    this.items = items;
    return this;
  }

  public CheckoutResult addItemsItem(CheckoutItem itemsItem) {
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
  @Valid 
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
    CheckoutResult checkoutResult = (CheckoutResult) o;
    return Objects.equals(this.orderId, checkoutResult.orderId) &&
        Objects.equals(this.customerId, checkoutResult.customerId) &&
        Objects.equals(this.totalAmount, checkoutResult.totalAmount) &&
        Objects.equals(this.status, checkoutResult.status) &&
        Objects.equals(this.items, checkoutResult.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, customerId, totalAmount, status, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckoutResult {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

