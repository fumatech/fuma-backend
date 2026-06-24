package com.backend.Repository.reports;

import com.backend.Entity.reports.InventoryAging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InventoryAgingRepository extends JpaRepository<InventoryAging, Long> {
    // Find all aging records for a warehouse
    List<InventoryAging> findByWarehouseId(Long warehouseId);

    // Find by warehouse and aging days
    List<InventoryAging> findByWarehouseIdAndAgingDaysGreaterThanEqual(Long warehouseId, Integer agingDays);
}
