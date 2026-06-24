package com.backend.Repository.reports;

import com.backend.Entity.reports.WarehousePerformanceMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WarehousePerformanceMetricsRepository extends JpaRepository<WarehousePerformanceMetrics, Long> {
    // Find all metrics for a warehouse
    List<WarehousePerformanceMetrics> findByWarehouseId(Long warehouseId);

    // Find by warehouse and date
    WarehousePerformanceMetrics findByWarehouseIdAndSnapshotDate(Long warehouseId, java.time.LocalDate snapshotDate);
}
