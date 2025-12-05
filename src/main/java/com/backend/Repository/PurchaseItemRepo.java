package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PurchaseItem;

@Repository
public interface PurchaseItemRepo extends JpaRepository<PurchaseItem, Long> {

}
