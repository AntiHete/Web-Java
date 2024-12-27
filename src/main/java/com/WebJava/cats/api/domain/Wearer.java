package com.WebJava.cats.api.domain;

import lombok.Getter;

@Getter
@RequiredArgsConstructor
public enum Wearer {

    CATS("cats-products"),

    KITTIES("kitty-products");


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
