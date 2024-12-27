package com.WebJava.cats.api.repository.entity;

import jakarta.persistence.*;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the 'Category' entity in the database.
 * This entity maps to the 'category' table and holds information about product categories.
 */
@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {

    /**
     * The unique identifier for the category.
     * Uses a sequence generator for ID generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_gen")
    @SequenceGenerator(name = "category_id_gen", sequenceName = "category_id_seq", allocationSize = 1)
    private Long id;

    /**
     * The name of the category.
     */
    private String name;

    /**
     * A list of products associated with this category.
     * Uses a lazy fetch strategy to optimize performance.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductEntity> products;
}
