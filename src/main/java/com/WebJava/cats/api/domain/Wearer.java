package com.WebJava.cats.api.domain;

import lombok.Getter;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the different types of wearers for products.
 */
@Getter
@RequiredArgsConstructor
public enum Wearer {

    /**
     * Represents the "cats" category of products.
=======

/**
 * Enumeration representing types of wearers for products.
 * Each enum constant is associated with a specific product category name.
 */
@Getter
public enum Wearer {

    /**
     * Represents products for cats.
>>>>>>> Lab3
     */
    CATS("cats-products"),

    /**
<<<<<<< HEAD
     * Represents the "kitties" category of products.
=======
     * Represents products for kitties.
>>>>>>> Lab3
     */
    KITTIES("kitty-products");

    /**
<<<<<<< HEAD
     * The name associated with the wearer.
=======
     * The name associated with the wearer type.
>>>>>>> Lab3
     */
    private final String wearerName;

    /**
<<<<<<< HEAD
     * Returns the Wearer enum instance for the given wearer name.
     *
     * @param wearerName the name of the wearer.
     * @return the corresponding Wearer enum.
     * @throws IllegalArgumentException if no Wearer is found for the given name.
     */
    public static Wearer fromString(String wearerName) {
        for (Wearer wearer : Wearer.values()) {
            if (wearer.getWearerName().equalsIgnoreCase(wearerName)) {
                return wearer;
            }
        }
        throw new IllegalArgumentException("No Wearer found for name: " + wearerName);
=======
     * Constructor to associate a wearer type with a specific name.
     *
     * @param wearerName the name of the product category for the wearer.
     */
    Wearer(String wearerName) {
        this.wearerName = wearerName;
>>>>>>> Lab3
    }
}
