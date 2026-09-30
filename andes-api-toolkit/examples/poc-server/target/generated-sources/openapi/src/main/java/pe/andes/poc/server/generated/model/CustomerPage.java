package pe.andes.poc.server.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import pe.andes.api.common.model.Pagination;
import pe.andes.poc.server.generated.model.Customer;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CustomerPage
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-30T00:08:29.761036194-05:00[America/Lima]", comments = "Generator version: 7.11.0")
public class CustomerPage {

  @Valid
  private List<@Valid Customer> content = new ArrayList<>();

  private @Nullable Pagination pagination;

  public CustomerPage content(List<@Valid Customer> content) {
    this.content = content;
    return this;
  }

  public CustomerPage addContentItem(Customer contentItem) {
    if (this.content == null) {
      this.content = new ArrayList<>();
    }
    this.content.add(contentItem);
    return this;
  }

  /**
   * Get content
   * @return content
   */
  @Valid 
  @JsonProperty("content")
  public List<@Valid Customer> getContent() {
    return content;
  }

  public void setContent(List<@Valid Customer> content) {
    this.content = content;
  }

  public CustomerPage pagination(Pagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   * @return pagination
   */
  @Valid 
  @JsonProperty("pagination")
  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerPage customerPage = (CustomerPage) o;
    return Objects.equals(this.content, customerPage.content) &&
        Objects.equals(this.pagination, customerPage.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, pagination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerPage {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
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

