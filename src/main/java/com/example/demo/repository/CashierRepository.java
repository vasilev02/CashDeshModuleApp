package com.example.demo.repository;

import com.example.demo.model.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CashierRepository interface is used to make the connection via Spring Data Jpa to our db table Cashier.
 */
@Repository
public interface CashierRepository extends JpaRepository<Cashier, Long> {
    Cashier findByName(String name);
    Cashier findByApiKey(String key);
}
