package com.WebJava.cats.api.domain.order;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
 * Represents a customer's order, containing items from a shopping cart.
 */
@Value
@Builder(toBuilder = true)
public class Order {

    /**
     * The unique identifier of the order.
     */
    @NonNull
    Long id;

    /**
     * The identifier of the associated shopping cart.
     */
    @NonNull
    String cartId;

    /**
     * The total price of the order.
     */
    @NonNull
    BigDecimal totalPrice;

    /**
     * The list of order entries (items).
     */
    @NonNull
    @Singular
    List<OrderEntry> entries;

    /**
     * Returns an unmodifiable view of the order entries list.
     *
     * @return the list of order entries.
     */
    public List<OrderEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
