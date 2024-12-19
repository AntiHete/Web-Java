package com.WebJava.cats.api.domain.order;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
<<<<<<< HEAD
 * Represents the context of an order, containing information about the cart, items, and total price.
=======
 * Represents the context of an order, including the cart ID, order entries, and total price.
>>>>>>> Lab3
 */
@Value
@Builder(toBuilder = true)
public class OrderContext {
<<<<<<< HEAD

    /**
     * The identifier of the associated shopping cart.
     */
    @NonNull
    String cartId;

    /**
     * The list of order entries (items) in the cart.
     */
    @NonNull
    @Singular
    List<OrderEntry> entries;

    /**
     * The total price of all items in the cart.
     */
    @NonNull
    BigDecimal totalPrice;

    /**
     * Returns an unmodifiable view of the order entries list.
     *
     * @return the list of order entries.
     */
    public List<OrderEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
=======
    
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
>>>>>>> Lab3
}
