package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.StockTransfer;

@Repository
public interface StockTransferRepo extends JpaRepository<StockTransfer, Long> {

}
