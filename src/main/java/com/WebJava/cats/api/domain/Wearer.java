package com.WebJava.cats.api.domain;

import lombok.Getter;

import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Wearer {

    CATS("cats-products"),

    KITTIES("kitty-products");


    private final String wearerName;


    public static Wearer fromString(String wearerName) {
        for (Wearer wearer : Wearer.values()) {
            if (wearer.getWearerName().equalsIgnoreCase(wearerName)) {
                return wearer;
            }
        }
        throw new IllegalArgumentException("No Wearer found for name: " + wearerName);

     * Constructor to associate a wearer type with a specific name.


    public static Wearer fromString(String wearerName) {
        for (Wearer wearer : Wearer.values()) {
            if (wearer.getWearerName().equalsIgnoreCase(wearerName)) {
                return wearer;
            }
        }
        throw new IllegalArgumentException("No Wearer found for name: " + wearerName);

    }
}
