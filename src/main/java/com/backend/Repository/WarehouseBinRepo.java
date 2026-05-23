package com.backend.Repository;

import com.backend.Entity.WarehouseBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseBinRepo extends JpaRepository<WarehouseBin, Long> {
    List<WarehouseBin> findByRackId(Long rackId);
    Optional<WarehouseBin> findByRackIdAndBinCode(Long rackId, String binCode);
}
