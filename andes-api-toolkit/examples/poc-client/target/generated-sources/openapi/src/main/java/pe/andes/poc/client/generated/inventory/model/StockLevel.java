package pe.andes.poc.client.generated.inventory.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * StockLevel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:37:54.929826446-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class StockLevel {

  private @Nullable String sku;

  private @Nullable String warehouse;

  private @Nullable Integer quantityAvailable;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime lastUpdated;

  public StockLevel sku(String sku) {
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

  public StockLevel warehouse(String warehouse) {
    this.warehouse = warehouse;
    return this;
  }

  /**
   * Get warehouse
   * @return warehouse
   */
  
  @JsonProperty("warehouse")
  public String getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(String warehouse) {
    this.warehouse = warehouse;
  }

  public StockLevel quantityAvailable(Integer quantityAvailable) {
    this.quantityAvailable = quantityAvailable;
    return this;
  }

  /**
   * Get quantityAvailable
   * @return quantityAvailable
   */
  
  @JsonProperty("quantityAvailable")
  public Integer getQuantityAvailable() {
    return quantityAvailable;
  }

  public void setQuantityAvailable(Integer quantityAvailable) {
    this.quantityAvailable = quantityAvailable;
  }

  public StockLevel lastUpdated(OffsetDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
    return this;
  }

  /**
   * Get lastUpdated
   * @return lastUpdated
   */
  
  @JsonProperty("lastUpdated")
  public OffsetDateTime getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(OffsetDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockLevel stockLevel = (StockLevel) o;
    return Objects.equals(this.sku, stockLevel.sku) &&
        Objects.equals(this.warehouse, stockLevel.warehouse) &&
        Objects.equals(this.quantityAvailable, stockLevel.quantityAvailable) &&
        Objects.equals(this.lastUpdated, stockLevel.lastUpdated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, warehouse, quantityAvailable, lastUpdated);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StockLevel {\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    warehouse: ").append(toIndentedString(warehouse)).append("\n");
    sb.append("    quantityAvailable: ").append(toIndentedString(quantityAvailable)).append("\n");
    sb.append("    lastUpdated: ").append(toIndentedString(lastUpdated)).append("\n");
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

