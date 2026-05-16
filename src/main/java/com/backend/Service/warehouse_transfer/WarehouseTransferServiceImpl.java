package com.backend.Service.warehouse_transfer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.warehouse_transfer.WarehouseTransferDispatch;
import com.backend.Entity.warehouse_transfer.WarehouseTransferItem;
import com.backend.Entity.warehouse_transfer.WarehouseTransferMaster;
import com.backend.Entity.warehouse_transfer.WarehouseTransferReceive;
import com.backend.Repository.warehouse_transfer.WarehouseTransferApprovalRepository;
import com.backend.Repository.warehouse_transfer.WarehouseTransferDispatchRepository;
import com.backend.Repository.warehouse_transfer.WarehouseTransferItemRepository;
import com.backend.Repository.warehouse_transfer.WarehouseTransferMasterRepository;
import com.backend.Repository.warehouse_transfer.WarehouseTransferReceiveRepository;
import com.backend.Repository.warehouse_transfer.WarehouseTransferStatusLogRepository;

@Service
public class WarehouseTransferServiceImpl implements WarehouseTransferService {

    @Autowired
    private WarehouseTransferMasterRepository masterRepo;
    @Autowired
    private WarehouseTransferItemRepository itemRepo;
    @Autowired
    private WarehouseTransferApprovalRepository approvalRepo;
    @Autowired
    private WarehouseTransferDispatchRepository dispatchRepo;
    @Autowired
    private WarehouseTransferReceiveRepository receiveRepo;
    @Autowired
    private WarehouseTransferStatusLogRepository statusLogRepo;

    @Override
    @Transactional
    public WarehouseTransferMaster createTransferRequest(WarehouseTransferMaster master, List<WarehouseTransferItem> items) {
        if (master == null) {
            throw new IllegalArgumentException("Transfer details (master) cannot be null");
        }
        master.setTransferNumber("TRF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        master.setCreatedAt(LocalDateTime.now());
        master.setUpdatedAt(LocalDateTime.now());
        if (master.getStatus() == null) master.setStatus("Draft");
        
        WarehouseTransferMaster savedMaster = masterRepo.save(master);
        
        if (items != null) {
            for (WarehouseTransferItem item : items) {
                item.setTransfer(savedMaster);
                itemRepo.save(item);
            }
        }
        return savedMaster;
    }

    @Override
    @Transactional
    public WarehouseTransferMaster approveTransfer(Long transferId, Long userId, String remarks, boolean approved) {
        WarehouseTransferMaster master = masterRepo.findById(transferId).orElse(null);
        if (master != null) {
            master.setStatus(approved ? "Approved" : "Rejected");
            master.setUpdatedAt(LocalDateTime.now());
            return masterRepo.save(master);
        }
        return null;
    }

    @Override
    @Transactional
    public WarehouseTransferDispatch dispatchTransfer(Long transferId, String vehicleDetails, String driverDetails, int dispatchQuantity, String dispatchNotes) {
        WarehouseTransferMaster master = masterRepo.findById(transferId).orElse(null);
        if (master != null) {
            master.setStatus("Dispatched");
            master.setUpdatedAt(LocalDateTime.now());
            masterRepo.save(master);
        }
        return null;
    }

    @Override
    @Transactional
    public WarehouseTransferReceive receiveTransfer(Long transferId, int receivedQuantity, int damagedQuantity, String receiverDetails, String receiveRemarks) {
        WarehouseTransferMaster master = masterRepo.findById(transferId).orElse(null);
        if (master != null) {
            master.setStatus("Received");
            master.setUpdatedAt(LocalDateTime.now());
            masterRepo.save(master);
        }
        return null;
    }

    @Override
    public WarehouseTransferMaster getTransferDetails(Long transferId) {
        return masterRepo.findById(transferId).orElse(null);
    }

    @Override
    public List<WarehouseTransferMaster> searchTransfers(String status, Long warehouseId, Long productId) {
        // Simple implementation for now: return all if no status provided
        if (status != null && !status.isEmpty()) {
            // You might need to add findByStatus to your repository later
            return masterRepo.findAll().stream().filter(m -> m.getStatus().equalsIgnoreCase(status)).toList();
        }
        return masterRepo.findAll();
    }

    @Override
    public void logStatus(Long transferId, String status, Long userId, String remarks) {
        // Log status change logic here
    }
}
