package com.example.demo.service;

import com.example.demo.DTO.CashierDto;
import com.example.demo.constant.Constants;
import com.example.demo.model.Cashier;
import com.example.demo.repository.CashierRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
public class CashierService {

    private static final Logger logger = LogManager.getLogger(CashierService.class);

    private final CashierRepository cashierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CashierService(CashierRepository cashierRepository, ModelMapper modelMapper) {
        this.cashierRepository = cashierRepository;
        this.modelMapper = modelMapper;
    }

    public CashierDto createCashier(CashierDto cashierDto) {

        this.checkIfCashierWithNameExist(cashierDto.getName());

        Cashier cashier = this.modelMapper.map(cashierDto, Cashier.class);

        cashier.setApiKey(UUID.randomUUID().toString());

        cashier.setDepositsCount(0);
        cashier.setWithdrawsCount(0);

        cashier.setAmountBGN(0);
        cashier.setAmountEUR(0);

        int[] quantitiesBGN = {0, 0, 0, 0, 0};
        cashier.setQuantitiesBGN(quantitiesBGN);

        int[] quantitiesEUR = {0, 0, 0, 0, 0, 0, 0};
        cashier.setQuantitiesEUR(quantitiesEUR);

        this.cashierRepository.save(cashier);
        logger.info("User with name " + cashierDto.getName() + " was created successfully!");
        return cashierDto;
    }

    public HashMap<String, String> balanceCheck(CashierDto cashierDto) {

        Cashier cashier = this.cashierRepository.findByName(cashierDto.getName());
        if (cashier == null) {
            throw new IllegalArgumentException("Cashier with that name does not exist!");
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

            for (int i = 0; i < quantitiesBGN.length; i++) {
                map.put(denominationsBGN.get(i) + " BGN", quantitiesBGN[i] + " times");
            }

            for (int i = 0; i < quantitiesEUR.length; i++) {
                map.put(denominationsEUR.get(i) + " EUR", quantitiesEUR[i] + " times");
            }

            cashier.setDepositsCount(0);
            cashier.setWithdrawsCount(0);
            this.cashierRepository.saveAndFlush(cashier);
            return map;
        }else{
            throw new IllegalArgumentException("You can't check the balance! Do two deposits and two withdraws!");
        }
    }

    private void checkIfCashierWithNameExist(String name) {
        Cashier cashier = this.cashierRepository.findByName(name);
        if (cashier != null) {
            throw new IllegalArgumentException("Cashier with that name already exist!");
        }
    }

}
