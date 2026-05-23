package com.backend.Controller;

import com.backend.Entity.WarehouseRack;
import com.backend.Entity.WarehouseBin;
import com.backend.DTO.WarehouseLocationDTO;
import com.backend.Service.WarehousePutAwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/putaway")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class WarehousePutAwayController {

    @Autowired
    private WarehousePutAwayService warehousePutAwayService;

    @GetMapping("/racks/{warehouseId}")
    public ResponseEntity<List<WarehouseRack>> getRacks(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(warehousePutAwayService.getRacksByWarehouse(warehouseId));
    }

    @PostMapping("/racks")
    public ResponseEntity<WarehouseRack> createRack(@RequestBody WarehouseRack rack) {
        return ResponseEntity.ok(warehousePutAwayService.createRack(rack));
    }

    @GetMapping("/bins/{rackId}")
    public ResponseEntity<List<WarehouseBin>> getBins(@PathVariable Long rackId) {
        return ResponseEntity.ok(warehousePutAwayService.getBinsByRack(rackId));
    }

    @PostMapping("/bins")
    public ResponseEntity<WarehouseBin> createBin(@RequestBody WarehouseBin bin) {
        return ResponseEntity.ok(warehousePutAwayService.createBin(bin));
    }

    @GetMapping("/unallocated/{warehouseId}")
    public ResponseEntity<List<Map<String, Object>>> getUnallocatedStock(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(warehousePutAwayService.getUnallocatedStock(warehouseId));
    }

    @PostMapping("/execute")
    public ResponseEntity<Map<String, String>> executePutAway(@RequestBody Map<String, Object> payload) {
        Long warehouseId = Long.valueOf(payload.get("warehouseId").toString());
        Long productId = Long.valueOf(payload.get("productId").toString());
        
        Long productVariationId = null;
        if (payload.get("productVariationId") != null && !payload.get("productVariationId").toString().equals("null")) {
            productVariationId = Long.valueOf(payload.get("productVariationId").toString());
        }

        Long rackId = Long.valueOf(payload.get("rackId").toString());
        Long binId = Long.valueOf(payload.get("binId").toString());
        Long quantity = Long.valueOf(payload.get("quantity").toString());
        
        Long userId = 1L; // Default user ID if none provided
        if (payload.get("userId") != null) {
            userId = Long.valueOf(payload.get("userId").toString());
        }

        warehousePutAwayService.performPutAway(warehouseId, productId, productVariationId, rackId, binId, quantity, userId);
        return ResponseEntity.ok(Map.of("message", "Stock put-away successfully completed"));
    }

    @GetMapping("/locations/{warehouseId}")
    public ResponseEntity<List<WarehouseLocationDTO>> getLocations(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(warehousePutAwayService.getInventoryLocations(warehouseId));
    }
}
