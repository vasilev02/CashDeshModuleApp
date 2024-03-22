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

    public CashierDto createCashier(CashierDto cashierDto){

        Cashier cashier = this.modelMapper.map(cashierDto, Cashier.class);

        cashier.setApiKey(UUID.randomUUID().toString());

        this.cashierRepository.save(cashier);
        logger.info("User with name " + cashierDto.getName() + " was created successfully!");
        logger.error("User with name " + cashierDto.getName() + " was created successfully!");
        logger.warn("User with name " + cashierDto.getName() + " was created successfully!");
        return cashierDto;
    }

}
