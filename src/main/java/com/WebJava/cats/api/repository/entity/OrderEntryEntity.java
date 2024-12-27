package com.WebJava.cats.api.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the 'OrderEntry' entity in the database.
 * This entity maps to the 'order_entry' table and holds information about individual order items.
 */
@Getter
@Setter
@Entity
@Table(name = "order_entry")
public class OrderEntryEntity {

    /**
     * The unique identifier for the order entry.
     * Uses a sequence generator for ID generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_entry_id_seq")
    @SequenceGenerator(name = "orders_entry_id_seq", sequenceName = "order_entry_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The quantity of the product in the order entry.
     * Cannot be null as every order entry must have a quantity.
     */
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * The order to which this order entry belongs.
     * Uses cascading operations for persist and merge actions.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    /**
     * The product associated with this order entry.
     * This field is required and eagerly fetched.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}
