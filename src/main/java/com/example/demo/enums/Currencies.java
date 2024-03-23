package com.example.demo.enums;

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
