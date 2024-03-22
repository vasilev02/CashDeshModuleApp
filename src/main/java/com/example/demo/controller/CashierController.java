package com.example.demo.controller;

import com.example.demo.DTO.CashierDto;
import com.example.demo.model.Cashier;
import com.example.demo.service.CashierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CashierController {

    private final CashierService cashierService;

    public CashierController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @PostMapping("/create-cashier")
    public ResponseEntity<CashierDto> createStudent(@RequestBody CashierDto cashierDto) {

        CashierDto newlyCreatedCashier = this.cashierService.createCashier(cashierDto);

        return new ResponseEntity<>(newlyCreatedCashier, HttpStatus.CREATED);
    }

}
