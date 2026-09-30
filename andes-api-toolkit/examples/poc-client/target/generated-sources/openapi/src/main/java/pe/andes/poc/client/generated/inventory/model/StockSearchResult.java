package pe.andes.poc.client.generated.inventory.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import pe.andes.poc.client.generated.inventory.model.StockLevel;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * StockSearchResult
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:08:30.749518838-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class StockSearchResult {

  
  private List<StockLevel> results = new ArrayList<>();

  private @Nullable String nextCursor = null;

  public StockSearchResult results(List<StockLevel> results) {
    this.results = results;
    return this;
  }

  public StockSearchResult addResultsItem(StockLevel resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Get results
   * @return results
   */
  
  @JsonProperty("results")
  public List<StockLevel> getResults() {
    return results;
  }

  public void setResults(List<StockLevel> results) {
    this.results = results;
  }

  public StockSearchResult nextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
    return this;
  }

  /**
   * Get nextCursor
   * @return nextCursor
   */
  
  @JsonProperty("nextCursor")
  public String getNextCursor() {
    return nextCursor;
  }

  public void setNextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StockSearchResult stockSearchResult = (StockSearchResult) o;
    return Objects.equals(this.results, stockSearchResult.results) &&
        Objects.equals(this.nextCursor, stockSearchResult.nextCursor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(results, nextCursor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StockSearchResult {\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
    sb.append("    nextCursor: ").append(toIndentedString(nextCursor)).append("\n");
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

