package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PurchasePaymentMethod;
@Repository
public interface PurchasePaymentMethodRepo extends JpaRepository<PurchasePaymentMethod, Long> {

}
