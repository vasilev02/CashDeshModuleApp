package com.example.demo.service;

import com.example.demo.DTO.CashierDto;
import com.example.demo.model.Cashier;
import com.example.demo.repository.CashierRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        checkIfCashierWithNameExist(cashierDto.getName());

        Cashier cashier = this.modelMapper.map(cashierDto, Cashier.class);

        cashier.setApiKey(UUID.randomUUID().toString());
        cashier.setAmountBGN(1000);
        cashier.setAmountEUR(2000);

        int[] quantitiesBGN = {0, 0, 0, 0, 0};
        cashier.setQuantitiesBGN(quantitiesBGN);

        int[] quantitiesEUR = {0, 0, 0, 0, 0, 0, 0};
        cashier.setQuantitiesEUR(quantitiesEUR);

        this.cashierRepository.save(cashier);
        logger.info("User with name " + cashierDto.getName() + " was created successfully!");
        return cashierDto;
    }

    private void checkIfCashierWithNameExist(String name) {
        Cashier cashier = this.cashierRepository.findByName(name);
        if(cashier!=null){
            throw new IllegalArgumentException("Cashier with that name already exist!");
        }
    }

}
