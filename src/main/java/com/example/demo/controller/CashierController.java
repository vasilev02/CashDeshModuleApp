package com.example.demo.controller;

import com.example.demo.DTO.CashierDto;
import com.example.demo.exception.ApiException;
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
    public ResponseEntity<Object> createStudent(@RequestBody CashierDto cashierDto) {
        try {
            CashierDto newlyCreatedCashier = this.cashierService.createCashier(cashierDto);
            return new ResponseEntity<>(newlyCreatedCashier, HttpStatus.CREATED);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}
