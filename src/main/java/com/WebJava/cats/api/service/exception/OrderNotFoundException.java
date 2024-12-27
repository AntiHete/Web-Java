package com.WebJava.cats.api.service.exception;

import java.util.UUID;

/**
 * Exception thrown when an order is not found.
 * This exception extends the base NotFoundException to provide specific error handling for orders.
 */
public class OrderNotFoundException extends NotFoundException {

    // Template message for the exception, used to format the message with the cartId.
    public final static String ORDER_NOT_FOUND = "Order with this cartId %s not found";

    /**
     * Constructor that formats the exception message with the provided cartId.
     *
     * @param cartId The cartId of the order that was not found.
     */
    public OrderNotFoundException(UUID cartId) {
        // Format the message with the cartId and pass it to the parent constructor.
        super(String.format(ORDER_NOT_FOUND, cartId));
        // Set the domain to "Order" for more specific error handling.
        DOMAIN = "Order";
    }
}
