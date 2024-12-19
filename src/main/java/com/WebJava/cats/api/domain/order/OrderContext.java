package com.WebJava.cats.api.domain.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

/**
 * Represents the context of an order, including the cart ID, order entries, and total price.
 */
@Value
@Builder(toBuilder = true)
public class OrderContext {
    
    /**
     * Unique identifier for the cart.
     */
    UUID cartId;

    /**
     * List of entries in the order.
     */
    List<OrderEntry> entries;

    /**
     * Total price of all the items in the order.
     */
    BigDecimal totalPrice;
}
