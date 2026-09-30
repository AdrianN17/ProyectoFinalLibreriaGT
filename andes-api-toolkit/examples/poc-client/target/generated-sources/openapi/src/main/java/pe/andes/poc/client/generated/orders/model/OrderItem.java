package pe.andes.poc.client.generated.orders.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrderItem
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:54.754076244-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class OrderItem {

  private @Nullable String sku;

  private @Nullable Integer quantity;

  private @Nullable Double unitPrice;

  public OrderItem sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Get sku
   * @return sku
   */
  
  @JsonProperty("sku")
  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public OrderItem quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
   */
  
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public OrderItem unitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  /**
   * Get unitPrice
   * @return unitPrice
   */
  
  @JsonProperty("unitPrice")
  public Double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(this.sku, orderItem.sku) &&
        Objects.equals(this.quantity, orderItem.quantity) &&
        Objects.equals(this.unitPrice, orderItem.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, quantity, unitPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderItem {\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
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

