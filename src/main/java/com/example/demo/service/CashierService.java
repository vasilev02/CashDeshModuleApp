package com.example.demo.service;

import com.example.demo.DTO.CashierDto;
import com.example.demo.DTO.CashierRegisteredDto;
import com.example.demo.constant.Constants;
import com.example.demo.logger.FileLogger;
import com.example.demo.model.Cashier;
import com.example.demo.repository.CashierRepository;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * CashierService class is a service clas which deal with the business logic regarding Cashier model .
 */
@Service
public class CashierService {

    private final CashierRepository cashierRepository;
    private final ModelMapper modelMapper;
    private final FileLogger fileLoggerUserCreation;
    private final FileLogger fileLoggerCashBalance;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CashierService(CashierRepository cashierRepository, ModelMapper modelMapper, @Qualifier("fileLoggerUserCreation") FileLogger fileLoggerUserCreation, @Qualifier("fileLoggerCashBalance") FileLogger fileLoggerCashBalance, PasswordEncoder passwordEncoder) {
        this.cashierRepository = cashierRepository;
        this.modelMapper = modelMapper;
        this.fileLoggerUserCreation = fileLoggerUserCreation;
        this.fileLoggerCashBalance = fileLoggerCashBalance;
        this.passwordEncoder = passwordEncoder;
    }

    public CashierRegisteredDto createCashier(CashierDto cashierDto) {

        this.checkIfCashierWithNameExist(cashierDto.getName());

        Cashier cashier = this.modelMapper.map(cashierDto, Cashier.class);

        String uuid = UUID.randomUUID().toString();
        cashier.setApiKey(passwordEncoder.encode(uuid));

        cashier.setDepositsCount(0);
        cashier.setWithdrawsCount(0);

        cashier.setAmountBGN(Constants.AMOUNT_IN_BGN);
        cashier.setAmountEUR(Constants.AMOUNT_IN_EUR);

        int[] quantitiesBGN = {0, 50, 0, 10, 0};
        cashier.setQuantitiesBGN(quantitiesBGN);

        int[] quantitiesEUR = {0, 100, 0, 20, 0, 0, 0};
        cashier.setQuantitiesEUR(quantitiesEUR);

        this.cashierRepository.save(cashier);

        fileLoggerUserCreation.writeToLogFile("Log - - - " + OperationCheck.getCurrentTime());
        fileLoggerUserCreation.writeToLogFile("New cashier with name " + cashierDto.getName() + " was created!");

        return new CashierRegisteredDto(cashierDto.getName(), uuid);
    }

    public HashMap<String, String> balanceCheck(CashierDto cashierDto) {

        Cashier cashier = this.cashierRepository.findByName(cashierDto.getName());
        if (cashier == null) {
            throw new EntityNotFoundException("Cashier with that name does not exist!");
        }

        int[] quantitiesBGN = cashier.getQuantitiesBGN();
        int[] quantitiesEUR = cashier.getQuantitiesEUR();

        String[] printBGN = new String[quantitiesBGN.length];
        String[] printEUR = new String[quantitiesEUR.length];

        List<Integer> denominationsBGN = Constants.denominationsBGN;
        List<Integer> denominationsEUR = Constants.denominationsEUR;

        if (cashier.getDepositsCount() >= 2 && cashier.getWithdrawsCount() >= 2) {

            HashMap<String, String> map = new LinkedHashMap();
            map.put("Total amount BGN", String.valueOf(cashier.getAmountBGN()));
            map.put("Total amount EUR", String.valueOf(cashier.getAmountEUR()));

            fileLoggerCashBalance.writeToLogFile("Log - - - " + OperationCheck.getCurrentTime());
            fileLoggerCashBalance.writeToLogFile("Cashier with name " + cashier.getName() + " checked his balance!");
            fileLoggerCashBalance.writeToLogFile("Total amount BGN: " + cashier.getAmountBGN() + "and EUR: " + cashier.getAmountEUR());


            for (int i = 0; i < quantitiesBGN.length; i++) {
                map.put(denominationsBGN.get(i) + " BGN", quantitiesBGN[i] + " times");
                fileLoggerCashBalance.writeToLogFile(denominationsBGN.get(i) + " BGN - " + quantitiesBGN[i] + " times");

            }

            for (int i = 0; i < quantitiesEUR.length; i++) {
                map.put(denominationsEUR.get(i) + " EUR", quantitiesEUR[i] + " times");
                fileLoggerCashBalance.writeToLogFile(denominationsEUR.get(i) + " EUR - " + quantitiesEUR[i] + " times");
            }

            cashier.setDepositsCount(0);
            cashier.setWithdrawsCount(0);
            this.cashierRepository.saveAndFlush(cashier);
            return map;
        } else {
            throw new IllegalArgumentException("You can't check the balance! Do two deposits and two withdraws!");
        }
    }

    private void checkIfCashierWithNameExist(String name) {
        Cashier cashier = this.cashierRepository.findByName(name);
        if (cashier != null) {
            throw new EntityNotFoundException("Cashier with that name already exist!");
        }
    }

    public Cashier getApiKey(String apiKey) {
        List<Cashier> cashiers = this.cashierRepository.findAll();
        for (int i = 0; i < cashiers.size(); i++) {
            String cryptedKey = cashiers.get(i).getApiKey();
            if (passwordEncoder.matches(apiKey, cryptedKey)) {
                return cashiers.get(i);
            }
        }
        return null;
    }

}
