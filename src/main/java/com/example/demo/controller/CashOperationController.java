package com.example.demo.controller;

import com.example.demo.DTO.DepositDto;
import com.example.demo.exception.ApiException;
import com.example.demo.service.DepositService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cash-operation")
public class CashOperationController {

    private final DepositService depositService;

    public CashOperationController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposit")
        public ResponseEntity<Object> deposit(@RequestBody DepositDto depositDto) {
        try {
            return new ResponseEntity<>(this.depositService.deposit(depositDto), HttpStatus.CREATED);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}
