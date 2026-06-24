package com.backend.Repository.reports;

import com.backend.Entity.reports.StockMovementLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface StockMovementLogRepository extends JpaRepository<StockMovementLog, Long> {
    // Find all logs for a warehouse
    List<StockMovementLog> findByWarehouseId(Long warehouseId);

    // Find by warehouse and movement type
    List<StockMovementLog> findByWarehouseIdAndMovementType(Long warehouseId, String movementType);

    // Find by warehouse and date range
    List<StockMovementLog> findByWarehouseIdAndMovementDateBetween(Long warehouseId, LocalDateTime start, LocalDateTime end);
}
