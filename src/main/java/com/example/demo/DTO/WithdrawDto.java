package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawDto {

    private String currency;

    private int amount;

    private int[] banknotes;

    private String cashierName;

}