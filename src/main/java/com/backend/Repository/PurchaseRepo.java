package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Purchase;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long> {

}
