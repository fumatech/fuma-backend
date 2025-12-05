package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PaymentMethod;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Long>{
	
    @Query("SELECT p.name FROM PaymentMethod p WHERE p.isActive = true")
    List<String> findAllActivePaymentMethodNames();

}
