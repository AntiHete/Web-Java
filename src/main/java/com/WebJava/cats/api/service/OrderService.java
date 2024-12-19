package com.WebJava.cats.api.service;

import com.WebJava.cats.api.domain.order.Order;
import com.WebJava.cats.api.dto.order.OrderRequestEntry;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    /**
     * Adds items to an existing order or creates a new order if cartId is not provided.
     * 
     * @param cartId an existing cart ID to update, or empty if a new cart should be created
     * @param orderRequestEntryList a list of items to add to the order, including product names and quantities
     * @return the updated or newly created order
     */
    Order addItemsToOrder(UUID cartId, List<OrderRequestEntry> orderRequestEntryList);
}
