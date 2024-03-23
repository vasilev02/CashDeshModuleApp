package com.example.demo.service;

import com.example.demo.DTO.DepositDto;
import com.example.demo.constant.Constants;
import com.example.demo.model.Cashier;
import com.example.demo.repository.CashierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        validateAmount(depositDto.getAmount());

        if (depositDto.getCurrency().equals("BGN")) {

            checkGivenDenominationBanknotesBGN(depositDto.getBanknotes());
            int[] cashierQuantities = saveMoney(depositDto.getBanknotes(), cashier.getQuantitiesBGN());
            cashier.setQuantitiesBGN(cashierQuantities);
            this.cashierRepository.saveAndFlush(cashier);


        } else if (depositDto.getCurrency().equals("EUR")) {

        } else {
            throw new IllegalArgumentException("Invalid operation type!");
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

    private void validateAmount(double amount) {
        if (amount < 5) {
            throw new IllegalArgumentException("Invalid amount! Provide at least 5.");
        }
        if (amount % 5 != 0) {
            throw new IllegalArgumentException("We do not support coins! Only banknotes.");
        }
    }

    private void checkGivenDenominationBanknotesBGN(int[] banknotes) {
        if (banknotes.length % 2 != 0) {
            throw new IllegalArgumentException("You have domination without banknotes!");
        }

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

    private int[] saveMoney(int[] moneyToSave, int[] cashierCurrentMoney) {

        for (int i = 0; i < moneyToSave.length; i+=2) {
            int quantity = moneyToSave[i];
            int banknote = moneyToSave[i + 1];

            int indexOfBanknote = Constants.denominationsBGN.indexOf(banknote);
            cashierCurrentMoney[indexOfBanknote] = cashierCurrentMoney[indexOfBanknote] + quantity;
        }
        return cashierCurrentMoney;
    }

}
