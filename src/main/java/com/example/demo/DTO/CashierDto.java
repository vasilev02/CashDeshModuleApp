package com.example.demo.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashierDto {

    @Column(name = "name", nullable = false)
    private String name;

}
