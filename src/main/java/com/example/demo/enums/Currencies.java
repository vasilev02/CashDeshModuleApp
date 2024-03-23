package com.example.demo.enums;

/**
 * Enum class to display all available currencies which our app is using.
 */
public enum Currencies {
    BGN("BGN"),EUR("EUR");

    private String name;

    Currencies(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
