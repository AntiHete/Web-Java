package com.WebJava.cats.api.domain.order;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderContext {

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
