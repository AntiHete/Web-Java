package com.WebJava.cats.api.domain;

import lombok.Getter;

/**
 * Enumeration representing types of wearers for products.
 * Each enum constant is associated with a specific product category name.
 */
@Getter
public enum Wearer {

    /**
     * Represents products for cats.
     */
    CATS("cats-products"),

    /**
     * Represents products for kitties.
     */
    KITTIES("kitty-products");

    /**
     * The name associated with the wearer type.
     */
    private final String wearerName;

    /**
     * Constructor to associate a wearer type with a specific name.
     *
     * @param wearerName the name of the product category for the wearer.
     */
    Wearer(String wearerName) {
        this.wearerName = wearerName;
    }
}
