package com.example.demo.service;

import com.example.demo.constant.Constants;
import com.example.demo.enums.Currencies;

import java.util.List;

/**
 * OperationCheck class is a class with common methods between some services to check some data.
 */
public class OperationCheck {

    protected static void validateAmount(int amount, int[] banknotes) {

        if (banknotes.length % 2 != 0) {
            throw new IllegalArgumentException("You have domination without banknotes!");
        }

        if (amount < 5) {
            throw new IllegalArgumentException("Invalid amount! Provide at least 5.");
        }
        if (amount % 5 != 0) {
            throw new IllegalArgumentException("We do not support coins! Only banknotes.");
        }

        int total = 0;
        for (int i = 0; i < banknotes.length; i += 2) {
            total += banknotes[i] * banknotes[i + 1];
        }
        if (amount != total) {
            throw new IllegalArgumentException("Amount does not equal to provided banknotes - " +
                    "Amount-" + amount + " / Given banknotes-" + total);
        }
    }

    protected static void checkGivenDenominationBanknotesBGN(int[] banknotes) {

        for (int i = 0; i < banknotes.length; i += 2) {
            if (banknotes[i] <= 0) {
                throw new IllegalArgumentException("Invalid quantity " + banknotes[i] + " for denomination of " + banknotes[i + 1] + "BGN!");
            }
        }

        for (int i = 1; i < banknotes.length; i += 2) {
            if (!Constants.denominationsBGN.contains(banknotes[i])) {
                throw new IllegalArgumentException("Invalid denomination of " + banknotes[i] + "BGN for quantity " + banknotes[i - 1] + "!");
            }
        }
    }

    protected static void checkGivenDenominationBanknotesEUR(int[] banknotes) {

        for (int i = 0; i < banknotes.length; i += 2) {
            if (banknotes[i] <= 0) {
                throw new IllegalArgumentException("Invalid quantity " + banknotes[i] + " for denomination of " + banknotes[i + 1] + "EUR!");
            }
        }

        for (int i = 1; i < banknotes.length; i += 2) {
            if (!Constants.denominationsEUR.contains(banknotes[i])) {
                throw new IllegalArgumentException("Invalid denomination of " + banknotes[i] + "EUR for quantity " + banknotes[i - 1] + "!");
            }
        }
    }

    protected static void checkIfAmountIsEnough(int amount, int[] cashierMoney, Currencies currency) {
        List<Integer> denominationToInteract;
        if (currency.getName().equals("BGN")) {
            denominationToInteract = Constants.denominationsBGN;
        } else {
            denominationToInteract = Constants.denominationsEUR;
        }
        int totalCashierAmount = 0;
        for (int i = 0; i < cashierMoney.length; i++) {
            totalCashierAmount += cashierMoney[i] * denominationToInteract.get(i);
        }
        if(totalCashierAmount < amount){
            throw new IllegalArgumentException("Not enough money in cash desk!");
        }
    }

}
