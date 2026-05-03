package com.backend.Controller;

import com.backend.Service.WarehouseActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/warehouse-action")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class WarehouseActionController {

    @Autowired
    private WarehouseActionService warehouseActionService;

    @PostMapping("/receive-transfer/{transferId}")
    public ResponseEntity<?> receiveTransfer(@PathVariable Long transferId, @RequestParam Long warehouseId) {
        try {
            warehouseActionService.receiveTransfer(transferId, warehouseId);
            return ResponseEntity.ok(Map.of("message", "Stock received successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dispatch-order/{orderId}")
    public ResponseEntity<?> dispatchOrder(@PathVariable Long orderId, @RequestParam Long warehouseId) {
        try {
            warehouseActionService.dispatchOrder(orderId, warehouseId);
            return ResponseEntity.ok(Map.of("message", "Order dispatched successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/initiate-transfer/{transferId}")
    public ResponseEntity<?> initiateTransfer(@PathVariable Long transferId, @RequestParam Long fromWarehouseId) {
        try {
            warehouseActionService.initiateTransfer(transferId, fromWarehouseId);
            return ResponseEntity.ok(Map.of("message", "Stock transfer initiated (In Transit)"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
