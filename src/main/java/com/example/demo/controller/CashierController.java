package com.example.demo.controller;

import com.example.demo.DTO.CashierDto;
import com.example.demo.exception.ApiException;
import com.example.demo.service.CashierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * CashierController class is responsible for processing incoming REST API requests.
 *
 * After request being invoked, the controller methods starts to process the web
 * request by interacting with the service layer in CashierService to complete the work that needs to be done.
 */
@RestController
@RequestMapping("/api/v1")
public class CashierController {

    private final CashierService cashierService;

    public CashierController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    /**
     *The method is used to create new cashier.
     *
     * @param cashierDto consist of name
     * @return either newlyCreatedCashier or Exception info
     * @throws jakarta.persistence.EntityNotFoundException when cashier does not exist
     */
    @PostMapping("/create-cashier")
    public ResponseEntity<Object> createCashier(@RequestBody CashierDto cashierDto) {
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
