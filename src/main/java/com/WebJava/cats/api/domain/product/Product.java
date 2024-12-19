package com.WebJava.cats.api.domain.product;

import com.WebJava.cats.api.domain.category.Category;

import java.math.BigDecimal;

import lombok.Builder;

import lombok.Value;

/**
 * Represents a product with details such as ID, name, description, price, stock quantity, and category.
 */
@Value
@Builder(toBuilder = true)
public class Product {

    /**
     * Unique identifier for the product.
     */
    Long id;

    /**
     * Name of the product.
     */
    String name;

    /**
     * Detailed description of the product.
     */
    String description;

    /**
     * Price of the product.
     */
    BigDecimal price;

    /**
     * Available quantity of the product in stock.
     */
    Integer stockQuantity;

    /**
     * Category to which the product belongs.
     */
    Category category;
}
