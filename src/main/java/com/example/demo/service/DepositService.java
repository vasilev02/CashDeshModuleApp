package com.example.demo.service;

import com.example.demo.DTO.DepositDto;
import com.example.demo.constant.Constants;
import com.example.demo.enums.Currencies;
import com.example.demo.model.Cashier;
import com.example.demo.repository.CashierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositService {

    private final CashierRepository cashierRepository;
    private final ModelMapper modelMapper;

    public DepositService(CashierRepository cashierRepository, ModelMapper modelMapper) {
        this.cashierRepository = cashierRepository;
        this.modelMapper = modelMapper;
    }

    public DepositDto deposit(DepositDto depositDto) {

        Cashier cashier = getCashier(depositDto.getCashierName());
        validateAmount(depositDto.getAmount(), depositDto.getBanknotes());

        if (depositDto.getCurrency().equals("BGN")) {

            checkGivenDenominationBanknotesBGN(depositDto.getBanknotes());
            int[] cashierQuantities = saveMoney(depositDto.getBanknotes(), cashier.getQuantitiesBGN(), Currencies.BGN);
            cashier.setQuantitiesBGN(cashierQuantities);
            cashier.setAmountBGN(cashier.getAmountBGN() + depositDto.getAmount());
            this.cashierRepository.saveAndFlush(cashier);


        } else if (depositDto.getCurrency().equals("EUR")) {

            checkGivenDenominationBanknotesEUR(depositDto.getBanknotes());
            int[] cashierQuantities = saveMoney(depositDto.getBanknotes(), cashier.getQuantitiesEUR(), Currencies.EUR);
            cashier.setQuantitiesEUR(cashierQuantities);
            cashier.setAmountEUR(cashier.getAmountEUR() + depositDto.getAmount());
            this.cashierRepository.saveAndFlush(cashier);

        } else {
            throw new IllegalArgumentException("Invalid operation type! Choose between BGN or EUR!");
        }
        return depositDto;
    }

    private Cashier getCashier(String name) {
        Cashier cashier = this.cashierRepository.findByName(name);
        if (cashier != null) {
            return cashier;
        }
        throw new IllegalArgumentException("Invalid cashier name!");
    }

    private void validateAmount(int amount, int[] banknotes) {

        if (banknotes.length % 2 != 0) {
            throw new IllegalArgumentException("You have domination without banknotes!");
        }

        if (amount < 5) {
            throw new IllegalArgumentException("Invalid amount! Provide at least 5.");
        }
        if (amount % 5 != 0) {
            throw new IllegalArgumentException("We do not support coins! Only banknotes.");
        }

        int totalToAdd = 0;
        for (int i = 0; i < banknotes.length; i += 2) {
            totalToAdd += banknotes[i] * banknotes[i+1];
        }
        if (amount != totalToAdd) {
            throw new IllegalArgumentException("Amount does not equal to provided banknotes - " +
                    "Amount-" + amount + " / Given banknotes-" + totalToAdd);
        }
    }

    private void checkGivenDenominationBanknotesBGN(int[] banknotes) {

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

    private void checkGivenDenominationBanknotesEUR(int[] banknotes) {

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

    private int[] saveMoney(int[] moneyToSave, int[] cashierCurrentMoney, Currencies currency) {

        List<Integer> denominationToInteract;
        if (currency.getName().equals("BGN")) {
            denominationToInteract = Constants.denominationsBGN;
        } else {
            denominationToInteract = Constants.denominationsEUR;
        }

        for (int i = 0; i < moneyToSave.length; i += 2) {
            int quantity = moneyToSave[i];
            int banknote = moneyToSave[i + 1];

            int indexOfBanknote = denominationToInteract.indexOf(banknote);
            cashierCurrentMoney[indexOfBanknote] = cashierCurrentMoney[indexOfBanknote] + quantity;
        }
        return cashierCurrentMoney;
    }
}
