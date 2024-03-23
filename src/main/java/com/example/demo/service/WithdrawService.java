package com.example.demo.service;

import com.example.demo.DTO.WithdrawDto;
import com.example.demo.constant.Constants;
import com.example.demo.enums.Currencies;
import com.example.demo.model.Cashier;
import com.example.demo.repository.CashierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * WithdrawService class is a service clas which deal with the business logic regarding withdraw operation.
 */
@Service
public class WithdrawService {

    private final CashierRepository cashierRepository;

    public WithdrawService(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    public WithdrawDto withdraw(WithdrawDto withdrawDto) {

        Cashier cashier = getCashier(withdrawDto.getCashierName());
        OperationCheck.validateAmount(withdrawDto.getAmount(), withdrawDto.getBanknotes());

        if (withdrawDto.getCurrency().equals("BGN")) {

            OperationCheck.checkIfAmountIsEnough(withdrawDto.getAmount(), cashier.getQuantitiesBGN(), Currencies.BGN);
            OperationCheck.checkGivenDenominationBanknotesBGN(withdrawDto.getBanknotes());
            int[] cashierQuantities = withdrawMoney(withdrawDto.getBanknotes(), cashier.getQuantitiesBGN(), Currencies.BGN);
            cashier.setQuantitiesBGN(cashierQuantities);
            cashier.setAmountBGN(cashier.getAmountBGN() - withdrawDto.getAmount());
            cashier.setWithdrawsCount(cashier.getWithdrawsCount() + 1);
            this.cashierRepository.saveAndFlush(cashier);


        } else if (withdrawDto.getCurrency().equals("EUR")) {

            OperationCheck.checkIfAmountIsEnough(withdrawDto.getAmount(), cashier.getQuantitiesEUR(), Currencies.EUR);
            OperationCheck.checkGivenDenominationBanknotesEUR(withdrawDto.getBanknotes());
            int[] cashierQuantities = withdrawMoney(withdrawDto.getBanknotes(), cashier.getQuantitiesEUR(), Currencies.EUR);
            cashier.setQuantitiesEUR(cashierQuantities);
            cashier.setAmountEUR(cashier.getAmountEUR() - withdrawDto.getAmount());
            cashier.setWithdrawsCount(cashier.getWithdrawsCount() + 1);
            this.cashierRepository.saveAndFlush(cashier);

        } else {
            throw new IllegalArgumentException("Invalid operation type! Choose between BGN or EUR!");
        }
        return withdrawDto;
    }

    private Cashier getCashier(String name) {
        Cashier cashier = this.cashierRepository.findByName(name);
        if (cashier != null) {
            return cashier;
        }
        throw new IllegalArgumentException("Invalid cashier name!");
    }

    private int[] withdrawMoney(int[] moneyToWithdraw, int[] cashierCurrentQuantity, Currencies currency) {

        List<Integer> denominationToInteract;
        if (currency.getName().equals("BGN")) {
            denominationToInteract = Constants.denominationsBGN;
        } else {
            denominationToInteract = Constants.denominationsEUR;
        }

        for (int i = 0; i < moneyToWithdraw.length; i += 2) {
            int quantity = moneyToWithdraw[i];
            int banknote = moneyToWithdraw[i + 1];

            int indexOfBanknote = denominationToInteract.indexOf(banknote);

            if (cashierCurrentQuantity[indexOfBanknote] >= quantity) {
                cashierCurrentQuantity[indexOfBanknote] = cashierCurrentQuantity[indexOfBanknote] - quantity;
            } else {
                throw new IllegalArgumentException("We do not have a denomination " + quantity + " for " + banknote + currency.getName() + "!");
            }
        }
        return cashierCurrentQuantity;
    }

}
