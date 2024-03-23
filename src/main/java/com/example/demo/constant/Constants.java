package com.example.demo.constant;

import java.util.List;

/**
 * Constants class is used for constants and predefined values.
 */
public class Constants {

    public final static double AMOUNT_IN_BGN = 1000.0;
    public final static double AMOUNT_IN_EUR = 2000.0;

    public final static String FILE_PATH_CASHIER_CREATION = "src/main/resources/logs/userCreation.txt";
    public final static String FILE_PATH_DEPOSIT_OPERATION = "src/main/resources/logs/depositOperations.txt";
    public final static String FILE_PATH_WITHDRAW_OPERATION = "src/main/resources/logs/withdrawOperations.txt";
    public final static String FILE_PATH_CASH_BALANCE = "src/main/resources/logs/cashBalance.txt";
    public final static String API_KEY = "testkey";
    public final static List<Integer> denominationsBGN = List.of(5, 10, 20, 50, 100);
    public final static List<Integer> denominationsEUR = List.of(5, 10, 20, 50, 100, 200, 500);

}
