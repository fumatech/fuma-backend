package com.backend.ServiceImpl;

import com.backend.Entity.*;
import com.backend.Repository.SaleSoOrderRepo;
import com.backend.Repository.StockTransferRepo;
import com.backend.Service.WarehouseActionService;
import com.backend.Service.WarehouseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WarehouseActionServiceImpl implements WarehouseActionService {

    @Autowired
    private WarehouseStockService warehouseStockService;

    @Autowired
    private StockTransferRepo stockTransferRepo;

    @Autowired
    private SaleSoOrderRepo saleSoOrderRepo;

    @Override
    public void receiveTransfer(Long transferId, Long warehouseId) {
        StockTransfer transfer = stockTransferRepo.findById(transferId)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));

        if ("RECEIVED".equalsIgnoreCase(transfer.getStatus())) {
            throw new RuntimeException("Transfer already received");
        }

        for (StockTransferItems item : transfer.getStockTransferItems()) {
            warehouseStockService.updateStock(
                warehouseId,
                item.getProductId(),
                item.getProductVariationId(),
                item.getQuantity(),
                "TRANSFER_IN",
                transfer.getReferenceNumber(),
                transfer
            );
        }

        transfer.setStatus("RECEIVED");
        stockTransferRepo.save(transfer);
    }

    @Override
    public void dispatchOrder(Long orderId, Long warehouseId) {
        SaleSoOrder order = saleSoOrderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Production Validation: Mapping
        if (order.getWarehouseId() != null && !order.getWarehouseId().equals(warehouseId)) {
            throw new RuntimeException("Order is assigned to a different warehouse: " + order.getWarehouseId());
        }

        // Double Dispatch Prevention
        if ("DISPATCHED".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order has already been dispatched");
        }

        for (SaleSoItem item : order.getSaleSoItem()) {
            warehouseStockService.updateStock(
                warehouseId,
                item.getProductId(),
                item.getProductVariationId(),
                item.getQuantity(),
                "DISPATCH",
                order.getOrderId(),
                order
            );
        }

        order.setStatus("DISPATCHED");
        saleSoOrderRepo.save(order);
    }

    @Override
    public void initiateTransfer(Long transferId, Long fromWarehouseId) {
        StockTransfer transfer = stockTransferRepo.findById(transferId)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));

        if ("IN_TRANSIT".equalsIgnoreCase(transfer.getStatus())) {
            throw new RuntimeException("Transfer already in transit");
        }

        for (StockTransferItems item : transfer.getStockTransferItems()) {
            warehouseStockService.updateStock(
                fromWarehouseId,
                item.getProductId(),
                item.getProductVariationId(),
                item.getQuantity(),
                "TRANSFER_OUT",
                transfer.getReferenceNumber(),
                transfer
            );
        }

        transfer.setStatus("IN_TRANSIT");
        transfer.setSourceWarehouseId(fromWarehouseId);
        stockTransferRepo.save(transfer);
    }
}
