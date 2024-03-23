package com.example.demo.controller;

import com.example.demo.DTO.DepositDto;
import com.example.demo.DTO.WithdrawDto;
import com.example.demo.exception.ApiException;
import com.example.demo.service.DepositService;
import com.example.demo.service.WithdrawService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cash-operation")
public class CashOperationController {

    private final DepositService depositService;
    private final WithdrawService withdrawService;

    public CashOperationController(DepositService depositService, WithdrawService withdrawService) {
        this.depositService = depositService;
        this.withdrawService = withdrawService;
    }

    @PostMapping("/deposit")
        public ResponseEntity<Object> deposit(@RequestBody DepositDto depositDto) {
        try {
            return new ResponseEntity<>(this.depositService.deposit(depositDto), HttpStatus.CREATED);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(@RequestBody WithdrawDto withdrawDto) {
        try {
            return new ResponseEntity<>(this.withdrawService.withdraw(withdrawDto), HttpStatus.CREATED);
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}
