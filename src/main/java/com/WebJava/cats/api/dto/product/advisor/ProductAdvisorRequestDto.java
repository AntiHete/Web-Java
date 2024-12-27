package com.WebJava.cats.api.dto.product.advisor;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

/**
 * DTO representing a product advisor request, containing basic product information.
 */
@Value
@Builder
public class ProductAdvisorRequestDto {

    /**
     * The name of the product.
     * Must be a valid, non-null value.
     */
    String name;

    /**
     * A description of the product.
     * Should provide more details about the product.
     */
    String description;

    /**
     * The price of the product.
     * Should be a positive value.
     */
    BigDecimal price;
}
