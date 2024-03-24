package com.example.demo.controller;

import com.example.demo.DTO.CashierDto;
import com.example.demo.constant.Constants;
import com.example.demo.exception.ApiException;
import com.example.demo.model.Cashier;
import com.example.demo.service.CashierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * BalanceCheckController class is responsible for processing incoming REST API requests.
 *
 * After request being invoked, the controller methods starts to process the web
 * request by interacting with the service layer in CashierService to complete the work that needs to be done.
 */
@RestController
@RequestMapping("/api/v1/cash-balance")
public class BalanceCheckController {

    private final CashierService cashierService;

    public BalanceCheckController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    /**
     *The method is used to check the balance of cashier.
     *
     * @param course consist of id, type and name
     * @return either HashMap<String, String> or Exception info
     * @throws jakarta.persistence.EntityNotFoundException when cashier does not exist
     * @throws IllegalArgumentException when we try to get balance but we do not fulfill all requirements
     */
    @PostMapping("/check")
    public ResponseEntity<Object> balanceCheck(@RequestHeader(Constants.FIB_X_AUTH) String apiKey, @RequestBody CashierDto cashierDto) {
        try {
            Cashier cashier = cashierService.getApiKey(apiKey);
            if (cashier != null) {
                cashierDto.setName(cashier.getName());
                return new ResponseEntity<>(this.cashierService.balanceCheck(cashierDto), HttpStatus.CREATED);
            }
        }catch (Exception e){
            return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return buildResponseEntity(new ApiException(HttpStatus.UNAUTHORIZED, "Invalid api key!"));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }

}
