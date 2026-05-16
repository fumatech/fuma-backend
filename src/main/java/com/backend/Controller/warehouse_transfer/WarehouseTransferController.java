package com.backend.Controller.warehouse_transfer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.warehouse_transfer.WarehouseTransferDispatch;
import com.backend.Entity.warehouse_transfer.WarehouseTransferItem;
import com.backend.Entity.warehouse_transfer.WarehouseTransferMaster;
import com.backend.Entity.warehouse_transfer.WarehouseTransferReceive;
import com.backend.Service.warehouse_transfer.WarehouseTransferService;

@RestController
@RequestMapping("/warehouse-transfer")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class WarehouseTransferController {

    @Autowired
    private WarehouseTransferService transferService;

    @PostMapping("/request")
    public WarehouseTransferMaster createTransferRequest(@RequestBody TransferRequestDto dto) {
        // TODO: Map DTO to entities, call service
        return transferService.createTransferRequest(dto.getMaster(), dto.getItems());
    }

    @PostMapping("/approve")
    public WarehouseTransferMaster approveTransfer(@RequestParam Long transferId, @RequestParam Long userId, @RequestParam String remarks, @RequestParam boolean approved) {
        return transferService.approveTransfer(transferId, userId, remarks, approved);
    }

    @PostMapping("/dispatch")
    public WarehouseTransferDispatch dispatchTransfer(@RequestParam Long transferId, @RequestParam String vehicleDetails, @RequestParam String driverDetails, @RequestParam int dispatchQuantity, @RequestParam String dispatchNotes) {
        return transferService.dispatchTransfer(transferId, vehicleDetails, driverDetails, dispatchQuantity, dispatchNotes);
    }

    @PostMapping("/receive")
    public WarehouseTransferReceive receiveTransfer(@RequestParam Long transferId, @RequestParam int receivedQuantity, @RequestParam int damagedQuantity, @RequestParam String receiverDetails, @RequestParam String receiveRemarks) {
        return transferService.receiveTransfer(transferId, receivedQuantity, damagedQuantity, receiverDetails, receiveRemarks);
    }

    @GetMapping("/details/{id}")
    public WarehouseTransferMaster getTransferDetails(@PathVariable Long id) {
        return transferService.getTransferDetails(id);
    }

    @GetMapping("/search")
    public List<WarehouseTransferMaster> searchTransfers(@RequestParam(required = false) String status, @RequestParam(required = false) Long warehouseId, @RequestParam(required = false) Long productId) {
        return transferService.searchTransfers(status, warehouseId, productId);
    }

    // DTO for transfer request
    public static class TransferRequestDto {

        private WarehouseTransferMaster master;
        private List<WarehouseTransferItem> items;

        public WarehouseTransferMaster getMaster() {
            return master;
        }

        public void setMaster(WarehouseTransferMaster master) {
            this.master = master;
        }

        public List<WarehouseTransferItem> getItems() {
            return items;
        }

        public void setItems(List<WarehouseTransferItem> items) {
            this.items = items;
        }
    }
}
