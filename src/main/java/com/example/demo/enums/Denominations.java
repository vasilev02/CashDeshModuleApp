package com.example.demo.enums;

public enum Denominations {

    ONE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500);

    private final int value;

    Denominations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
