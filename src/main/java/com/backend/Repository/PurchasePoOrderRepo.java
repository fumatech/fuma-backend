package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PurchasePoOrder;
@Repository
public interface PurchasePoOrderRepo extends JpaRepository<PurchasePoOrder, Long> {
	

	  @Query("SELECT p FROM PurchasePoOrder p WHERE p.vendor LIKE CONCAT('%', :vendor, '%')")
	    List<PurchasePoOrder> findByVendorName(String vendor);

}
