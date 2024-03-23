package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cashiers")
public class Cashier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String apiKey;

    private int depositsCount;

    private int withdrawsCount;

    private double amountBGN;

    private double amountEUR;

    private int[] quantitiesBGN;

    private int[] quantitiesEUR;

}
