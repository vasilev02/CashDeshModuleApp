package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * CashierRegisteredDto class is DTO class used to transfer data between layers.
 */
@Getter
@Setter
@AllArgsConstructor
public class CashierRegisteredDto {

    private String name;

    private String apiKey;

}