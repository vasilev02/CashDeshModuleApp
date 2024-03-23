package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Cashier model class is used to create Cashier with ID and other properties when being saved in the PostgreDB.
 */
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
