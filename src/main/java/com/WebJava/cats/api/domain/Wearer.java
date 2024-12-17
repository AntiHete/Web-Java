package com.WebJava.cats.api.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the different types of wearers for products.
 */
@Getter
@RequiredArgsConstructor
public enum Wearer {

    /**
     * Represents the "cats" category of products.
     */
    CATS("cats-products"),

    /**
     * Represents the "kitties" category of products.
     */
    KITTIES("kitty-products");

    /**
     * The name associated with the wearer.
     */
    private final String wearerName;

    /**
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
    }
}
