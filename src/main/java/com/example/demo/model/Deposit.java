package com.example.demo.model;

import com.example.demo.enums.Denominations;
import jakarta.persistence.*;

import java.util.EnumMap;

@Entity
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private double amount;

    @ElementCollection
    @CollectionTable(name = "denomination_bgn_mapping",
            joinColumns = @JoinColumn(name = "entity_id"))
    @MapKeyColumn(name = "denomination_key")
    @Column(name = "denomination_value")
    private EnumMap<Denominations, Integer> denominations;

}
