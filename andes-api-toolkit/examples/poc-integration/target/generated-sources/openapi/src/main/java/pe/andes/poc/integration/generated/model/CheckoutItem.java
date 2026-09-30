package pe.andes.poc.integration.generated.model;

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
 * CheckoutItem
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:06:54.005441654-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class CheckoutItem {

  private String sku;

  private Integer quantity;

  private Double unitPrice;

  public CheckoutItem() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CheckoutItem(String sku, Integer quantity, Double unitPrice) {
    this.sku = sku;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public CheckoutItem sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Get sku
   * @return sku
   */
  @NotNull 
  @JsonProperty("sku")
  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public CheckoutItem quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
   */
  @NotNull 
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public CheckoutItem unitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  /**
   * Get unitPrice
   * @return unitPrice
   */
  @NotNull 
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
    CheckoutItem checkoutItem = (CheckoutItem) o;
    return Objects.equals(this.sku, checkoutItem.sku) &&
        Objects.equals(this.quantity, checkoutItem.quantity) &&
        Objects.equals(this.unitPrice, checkoutItem.unitPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, quantity, unitPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckoutItem {\n");
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

