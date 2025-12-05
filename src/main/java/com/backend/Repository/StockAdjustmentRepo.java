 package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Entity.StockAdjustment;

public interface StockAdjustmentRepo extends JpaRepository<StockAdjustment, Long> {

}
