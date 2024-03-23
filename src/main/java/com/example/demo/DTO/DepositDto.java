package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * DepositDto class is DTO class used to transfer data between layers.
 */
@Getter
@Setter
public class DepositDto {

    private String currency;

    private int amount;

    private int[] banknotes;

    private String cashierName;

}
