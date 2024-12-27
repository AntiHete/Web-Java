package com.WebJava.cats.api.dto.product;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.domain.category.Category;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

/**
 * DTO representing the data of a product for transferring to the client.
 */
@Value
@Builder
public class ProductDto {

    /**
     * The unique identifier of the product.
     */
    Long id;

    /**
     * The name of the product.
     */
    String name;

    /**
     * The description of the product.
     */
    String description;

    /**
     * The price of the product.
     */
    BigDecimal price;

    /**
     * The quantity of the product in stock.
     */
    Integer stockQuantity;

    /**
     * The category to which the product belongs.
     */
    Category category;

    /**
     * The wearer associated with the product.
     */
    Wearer wearer;
}
