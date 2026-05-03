package com.backend.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.StockTransfer;

@Repository
public interface StockTransferRepo extends JpaRepository<StockTransfer, Long> {

	List<StockTransfer> findByTargetWarehouseIdOrderByDateDesc(Long targetWarehouseId);

}
