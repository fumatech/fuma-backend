package com.backend.Service.warehouse_transfer;

import java.util.List;

import com.backend.Entity.warehouse_transfer.WarehouseTransferDispatch;
import com.backend.Entity.warehouse_transfer.WarehouseTransferItem;
import com.backend.Entity.warehouse_transfer.WarehouseTransferMaster;
import com.backend.Entity.warehouse_transfer.WarehouseTransferReceive;

public interface WarehouseTransferService {

    WarehouseTransferMaster createTransferRequest(WarehouseTransferMaster master, List<WarehouseTransferItem> items);

    WarehouseTransferMaster approveTransfer(Long transferId, Long userId, String remarks, boolean approved);

    WarehouseTransferDispatch dispatchTransfer(Long transferId, String vehicleDetails, String driverDetails, int dispatchQuantity, String dispatchNotes);

    WarehouseTransferReceive receiveTransfer(Long transferId, int receivedQuantity, int damagedQuantity, String receiverDetails, String receiveRemarks);

    WarehouseTransferMaster getTransferDetails(Long transferId);

    List<WarehouseTransferMaster> searchTransfers(String status, Long warehouseId, Long productId);

    void logStatus(Long transferId, String status, Long userId, String remarks);
}
