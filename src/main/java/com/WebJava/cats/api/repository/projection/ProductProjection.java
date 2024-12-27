package com.WebJava.cats.api.repository.projection;

import com.WebJava.cats.api.domain.Wearer;

/**
 * Projection interface for querying specific product details.
 * This interface is used to fetch selected fields of a Product entity without 
 * retrieving the full entity, improving performance for certain queries.
 */
public interface ProductProjection {

    /**
     * Gets the name of the product.
     * 
     * @return The product name
     */
    String getName();

    /**
     * Gets the description of the product.
     * 
     * @return The product description
     */
    String getDescription();

    /**
     * Gets the wearer of the product.
     * 
     * @return The wearer (e.g., CATS, KITTIES)
     */
    Wearer getWearer();

    /**
     * Gets the price of the product.
     * 
     * @return The price of the product
     */
    Double getPrice();
}
