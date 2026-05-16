package com.backend.Repository.warehouse_transfer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Entity.warehouse_transfer.WarehouseTransferItem;

public interface WarehouseTransferItemRepository extends JpaRepository<WarehouseTransferItem, Long> {
}
