package com.WebJava.cats.api.domain.order;

import com.WebJava.cats.api.domain.product.Product;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Represents a single item in an order, including the product and its quantity.
 */
@Value
@Builder(toBuilder = true)
public class OrderEntry {

    /**
     * The product in the order entry.
     */
    @NonNull
    Product product;

    /**
     * The quantity of the product in the order.
     */
    @NonNull
    Integer quantity;

    /**
     * Ensures the quantity is positive.
     *
     * @throws IllegalArgumentException if the quantity is less than or equal to zero.
     */
    public OrderEntry(Product product, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.product = product;
        this.quantity = quantity;
    }
}
