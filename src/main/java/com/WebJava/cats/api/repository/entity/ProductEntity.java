package com.WebJava.cats.api.repository.entity;

import com.WebJava.cats.api.domain.Wearer;
import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the 'Product' entity in the database.
 * This entity maps to the 'product' table and holds information about individual products.
 */
@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity {

    /**
     * The unique identifier for the product.
     * Uses a sequence generator for ID generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;

    /**
     * The name of the product.
     * This field is required for identifying the product.
     */
    private String name;

    /**
     * A detailed description of the product.
     * Provides additional information about the product's features or usage.
     */
    private String description;

    /**
     * The price of the product.
     * The column is marked as non-nullable, with a precision of 10 digits and 2 decimal places.
     */
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * The quantity of the product available in stock.
     * This field is required for inventory management.
     */
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    /**
     * The wearer type for this product.
     * The product can be associated with either 'CATS' or 'KITTIES'.
     */
    @Enumerated(EnumType.ORDINAL)
    private Wearer wearer;

    /**
     * The category to which this product belongs.
     * This field is required and establishes the relationship between product and category.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
}
