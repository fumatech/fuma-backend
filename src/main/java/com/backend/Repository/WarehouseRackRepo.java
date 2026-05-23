package com.backend.Repository;

import com.backend.Entity.WarehouseRack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRackRepo extends JpaRepository<WarehouseRack, Long> {
    List<WarehouseRack> findByWarehouseId(Long warehouseId);
    Optional<WarehouseRack> findByWarehouseIdAndRackCode(Long warehouseId, String rackCode);
}
