package com.example.demo.service;

import com.example.demo.DTO.DepositDto;
import com.example.demo.constant.Constants;
import com.example.demo.enums.Currencies;
import com.example.demo.logger.FileLogger;
import com.example.demo.model.Cashier;
import com.example.demo.repository.CashierRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DepositService class is a service clas which deal with the business logic regarding deposit operation.
 */
@Service
public class DepositService {

    private final CashierRepository cashierRepository;

    private final FileLogger fileLogger;

    public DepositService(CashierRepository cashierRepository, @Qualifier("fileLoggerDepositTransactions") FileLogger fileLogger) {
        this.cashierRepository = cashierRepository;
        this.fileLogger = fileLogger;
    }

    public DepositDto deposit(DepositDto depositDto) {

        Cashier cashier = getCashier(depositDto.getCashierName());
        OperationCheck.validateAmount(depositDto.getAmount(), depositDto.getBanknotes());

        if (depositDto.getCurrency().equals("BGN")) {

            OperationCheck.checkGivenDenominationBanknotesBGN(depositDto.getBanknotes());
            int[] cashierQuantities = depositMoney(depositDto.getBanknotes(), cashier.getQuantitiesBGN(), Currencies.BGN);
            cashier.setQuantitiesBGN(cashierQuantities);
            cashier.setAmountBGN(cashier.getAmountBGN() + depositDto.getAmount());
            cashier.setDepositsCount(cashier.getDepositsCount() + 1);
            this.cashierRepository.saveAndFlush(cashier);
            fileLogger.writeToLogFile("Log - - - " + OperationCheck.getCurrentTime());
            fileLogger.writeToLogFile("Cashier with name " + cashier.getName() + " has just deposited to the cash module " + depositDto.getAmount()+ "BGN!");

        } else if (depositDto.getCurrency().equals("EUR")) {

            OperationCheck.checkGivenDenominationBanknotesEUR(depositDto.getBanknotes());
            int[] cashierQuantities = depositMoney(depositDto.getBanknotes(), cashier.getQuantitiesEUR(), Currencies.EUR);
            cashier.setQuantitiesEUR(cashierQuantities);
            cashier.setAmountEUR(cashier.getAmountEUR() + depositDto.getAmount());
            cashier.setDepositsCount(cashier.getDepositsCount() + 1);
            this.cashierRepository.saveAndFlush(cashier);
            fileLogger.writeToLogFile("Log - - - " + OperationCheck.getCurrentTime());
            fileLogger.writeToLogFile("Cashier with name " + cashier.getName() + " has just deposited to the cash module " + depositDto.getAmount()+ "EUR!");

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

    private int[] depositMoney(int[] moneyToSave, int[] cashierCurrentQuantity, Currencies currency) {

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
            cashierCurrentQuantity[indexOfBanknote] = cashierCurrentQuantity[indexOfBanknote] + quantity;
        }
        return cashierCurrentQuantity;
    }
}
