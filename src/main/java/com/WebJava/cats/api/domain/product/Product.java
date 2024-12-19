package com.WebJava.cats.api.domain.product;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.domain.category.Category;

import java.math.BigDecimal;

import lombok.Builder;
<<<<<<< HEAD
import lombok.NonNull;
import lombok.Value;

/**
 * Represents a product available for purchase in the system.
=======

import lombok.Value;

/**
 * Represents a product with details such as ID, name, description, price, stock quantity, and category.
>>>>>>> Lab3
 */
@Value
@Builder(toBuilder = true)
public class Product {

    /**
<<<<<<< HEAD
     * The unique identifier of the product.
     */
    @NonNull
    Long id;

    /**
     * The name of the product.
     */
    @NonNull
    String name;

    /**
     * A description of the product.
     */
    @NonNull
    String description;

    /**
     * The price of the product.
     */
    @NonNull
    BigDecimal price;

    /**
     * The available stock quantity of the product.
     */
    @NonNull
    Integer stockQuantity;

    /**
     * The category to which the product belongs.
     */
    @NonNull
=======
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
>>>>>>> Lab3
    Category category;

    /**
     * The intended wearer of the product.
     */
    @NonNull
    Wearer wearer;

    /**
     * Ensures that the product's price and stock quantity are valid.
     *
     * @throws IllegalArgumentException if the price is negative or stock quantity is negative.
     */
    public Product(Long id, String name, String description, BigDecimal price, Integer stockQuantity, 
                   Category category, Wearer wearer) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.wearer = wearer;
    }
}
