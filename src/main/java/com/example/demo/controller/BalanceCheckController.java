package com.example.demo.controller;

import com.example.demo.DTO.CashierDto;
import com.example.demo.exception.ApiException;
import com.example.demo.service.CashierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cash-balance")
public class BalanceCheckController {

    private final CashierService cashierService;

    public BalanceCheckController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @PostMapping("/check")
    public ResponseEntity<Object> balanceCheck(@RequestBody CashierDto cashierDto) {
        try {
            return new ResponseEntity<>(this.cashierService.balanceCheck(cashierDto), HttpStatus.CREATED);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}
