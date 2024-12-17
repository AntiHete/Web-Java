package com.WebJava.cats.api.domain.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Represents a product category in the system.
 */
@Value
@Builder(toBuilder = true)
public class Category {

    /**
     * The unique identifier of the category.
     */
    @NonNull
    @JsonProperty("id")
    Long id;

    /**
     * The name of the category.
     */
    @NonNull
    @JsonProperty("name")
    String name;
}
