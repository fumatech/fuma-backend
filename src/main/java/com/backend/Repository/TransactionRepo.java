package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.backend.Entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
	
	
	@Query("SELECT p FROM Transaction p WHERE p.vendor LIKE CONCAT('%', :vendor, '%')")
    List<Transaction> findByVendorName(String vendor);
	
	
	@Query("SELECT p FROM Transaction p WHERE p.franchiseName LIKE CONCAT('%', :franchiseName, '%')")
    List<Transaction> findByfranchiseName(String franchiseName);
	
	
	

}
