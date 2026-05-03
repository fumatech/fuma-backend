package com.backend.Service;

public interface WarehouseActionService {
    void receiveTransfer(Long transferId, Long warehouseId);
    void dispatchOrder(Long orderId, Long warehouseId);
    void initiateTransfer(Long transferId, Long fromWarehouseId);
}
