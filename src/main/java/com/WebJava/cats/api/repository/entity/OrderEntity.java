package com.WebJava.cats.api.repository.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

/**
 * Represents the 'Order' entity in the database.
 * This entity maps to the 'orders' table and holds information about customer orders.
 */
@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {

    /**
     * The unique identifier for the order.
     * Uses a sequence generator for ID generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_gen")
    @SequenceGenerator(name = "orders_id_gen", sequenceName = "order_id_seq", allocationSize = 1)
    private Long id;

    /**
     * A natural ID to uniquely identify the cart associated with this order.
     * Ensures uniqueness at the database level.
     */
    @NaturalId
    @Column(nullable = false, unique = true)
    private UUID cartId;

    /**
     * The total price for the order.
     * Precision is set to 10 digits with 2 decimal places for currency.
     */
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    /**
     * A list of entries (order line items) associated with this order.
     * Uses cascade for persist and merge operations, and removes orphaned entries.
     */
    @OneToMany(
        mappedBy = "order",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<OrderEntryEntity> orderEntries;
}
