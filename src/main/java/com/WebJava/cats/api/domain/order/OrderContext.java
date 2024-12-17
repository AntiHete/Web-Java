package com.WebJava.cats.api.domain.order;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
 * Represents the context of an order, containing information about the cart, items, and total price.
 */
@Value
@Builder(toBuilder = true)
public class OrderContext {

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
}
