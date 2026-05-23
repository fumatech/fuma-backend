package com.backend.ServiceImpl;

import com.backend.Entity.*;
import com.backend.Repository.*;
import com.backend.Service.WarehousePutAwayService;
import com.backend.DTO.WarehouseLocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class WarehousePutAwayServiceImpl implements WarehousePutAwayService {

    @Autowired
    private WarehouseRackRepo warehouseRackRepo;

    @Autowired
    private WarehouseBinRepo warehouseBinRepo;

    @Autowired
    private WarehouseStockLocationRepo warehouseStockLocationRepo;

    @Autowired
    private WarehouseStockRepo warehouseStockRepo;

    @Override
    public List<WarehouseRack> getRacksByWarehouse(Long warehouseId) {
        return warehouseRackRepo.findByWarehouseId(warehouseId);
    }

    @Override
    public WarehouseRack createRack(WarehouseRack rack) {
        if (rack.getWarehouseId() == null || rack.getRackCode() == null || rack.getRackCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse ID and Rack Code are mandatory");
        }
        Optional<WarehouseRack> existing = warehouseRackRepo.findByWarehouseIdAndRackCode(
                rack.getWarehouseId(), rack.getRackCode().trim());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Rack code " + rack.getRackCode() + " already exists in this warehouse");
        }
        rack.setRackCode(rack.getRackCode().trim());
        if (rack.getStatus() == null) {
            rack.setStatus(RackStatus.ACTIVE);
        }
        return warehouseRackRepo.save(rack);
    }

    @Override
    public List<WarehouseBin> getBinsByRack(Long rackId) {
        return warehouseBinRepo.findByRackId(rackId);
    }

    @Override
    public WarehouseBin createBin(WarehouseBin bin) {
        if (bin.getRackId() == null || bin.getBinCode() == null || bin.getBinCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Rack ID and Bin Code are mandatory");
        }
        if (bin.getCapacity() == null || bin.getCapacity() <= 0) {
            throw new IllegalArgumentException("Bin capacity is mandatory and must be greater than 0");
        }
        Optional<WarehouseBin> existing = warehouseBinRepo.findByRackIdAndBinCode(
                bin.getRackId(), bin.getBinCode().trim());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Bin code " + bin.getBinCode() + " already exists in this rack");
        }
        bin.setBinCode(bin.getBinCode().trim());
        if (bin.getStatus() == null) {
            bin.setStatus(BinStatus.ACTIVE);
        }
        return warehouseBinRepo.save(bin);
    }

    @Override
    public List<Map<String, Object>> getUnallocatedStock(Long warehouseId) {
        List<WarehouseStock> stockList = warehouseStockRepo.findByWarehouseId(warehouseId);
        List<WarehouseStockLocation> locationList = warehouseStockLocationRepo.findByWarehouseId(warehouseId);

        // Group allocated stocks by productId and productVariationId
        Map<String, Long> allocatedMap = new HashMap<>();
        for (WarehouseStockLocation loc : locationList) {
            String key = loc.getProductId() + "_" + (loc.getProductVariationId() != null ? loc.getProductVariationId() : "null");
            allocatedMap.put(key, allocatedMap.getOrDefault(key, 0L) + loc.getQuantity());
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (WarehouseStock ws : stockList) {
            String key = ws.getProductId() + "_" + (ws.getProductVariationId() != null ? ws.getProductVariationId() : "null");
            Long allocated = allocatedMap.getOrDefault(key, 0L);
            Long total = ws.getQuantity();
            Long unallocated = total - allocated;

            if (unallocated > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("productId", ws.getProductId());
                map.put("productVariationId", ws.getProductVariationId());
                map.put("productName", ws.getProductName());
                map.put("productSku", ws.getProductSku());
                map.put("productVariationName", ws.getProductVariationName());
                map.put("totalQuantity", total);
                map.put("allocatedQuantity", allocated);
                map.put("unallocatedQuantity", unallocated);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public void performPutAway(Long warehouseId, Long productId, Long productVariationId, Long rackId, Long binId, Long quantity, Long userId) {
        if (warehouseId == null || productId == null || rackId == null || binId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Warehouse, Product, Rack, Bin, and positive Quantity are mandatory");
        }

        // VALIDATION 4: Rack must be ACTIVE
        WarehouseRack rack = warehouseRackRepo.findById(rackId)
                .orElseThrow(() -> new IllegalArgumentException("Rack not found"));
        if (rack.getStatus() != RackStatus.ACTIVE) {
            throw new IllegalArgumentException("Rack " + rack.getRackCode() + " is not ACTIVE");
        }

        // VALIDATION 3: Bin must be ACTIVE
        WarehouseBin bin = warehouseBinRepo.findById(binId)
                .orElseThrow(() -> new IllegalArgumentException("Bin not found"));
        if (bin.getStatus() != BinStatus.ACTIVE) {
            throw new IllegalArgumentException("Bin " + bin.getBinCode() + " is not ACTIVE");
        }
        if (!bin.getRackId().equals(rackId)) {
            throw new IllegalArgumentException("Selected bin does not belong to the selected rack");
        }

        // VALIDATION 1: Requested quantity must NOT exceed unallocated quantity
        WarehouseStock totalStock = warehouseStockRepo.findByWarehouseIdAndProductIdAndProductVariationId(
                warehouseId, productId, productVariationId)
                .orElseThrow(() -> new IllegalArgumentException("No warehouse stock records found for this product"));

        List<WarehouseStockLocation> existingAllocations = warehouseStockLocationRepo
                .findByWarehouseIdAndProductIdAndProductVariationId(warehouseId, productId, productVariationId);
        
        long totalAllocated = existingAllocations.stream().mapToLong(WarehouseStockLocation::getQuantity).sum();
        long unallocatedQty = totalStock.getQuantity() - totalAllocated;

        if (quantity > unallocatedQty) {
            throw new IllegalArgumentException("Requested quantity (" + quantity + ") exceeds unallocated quantity (" + unallocatedQty + ")");
        }

        // VALIDATION 2: Bin capacity must NOT exceed limit (existing bin qty + new qty <= capacity)
        List<WarehouseStockLocation> binStocks = warehouseStockLocationRepo.findByBinId(binId);
        long currentBinQty = binStocks.stream().mapToLong(WarehouseStockLocation::getQuantity).sum();
        if (currentBinQty + quantity > bin.getCapacity()) {
            throw new IllegalArgumentException("Putting away " + quantity + " units would exceed Bin capacity (Occupied: " 
                    + currentBinQty + "/" + bin.getCapacity() + ")");
        }

        // PUT-AWAY LOGIC
        Optional<WarehouseStockLocation> existingLocationStock = warehouseStockLocationRepo
                .findByWarehouseIdAndProductIdAndProductVariationIdAndRackIdAndBinId(
                        warehouseId, productId, productVariationId, rackId, binId);

        WarehouseStockLocation targetLocation;
        if (existingLocationStock.isPresent()) {
            targetLocation = existingLocationStock.get();
            targetLocation.setQuantity(targetLocation.getQuantity() + quantity);
        } else {
            targetLocation = new WarehouseStockLocation();
            targetLocation.setWarehouseId(warehouseId);
            targetLocation.setProductId(productId);
            targetLocation.setProductVariationId(productVariationId);
            targetLocation.setRackId(rackId);
            targetLocation.setBinId(binId);
            targetLocation.setQuantity(quantity);
        }
        targetLocation.setCreatedBy(userId);
        
        warehouseStockLocationRepo.save(targetLocation);
    }

    @Override
    public List<WarehouseLocationDTO> getInventoryLocations(Long warehouseId) {
        return warehouseStockLocationRepo.getInventoryLocations(warehouseId);
    }
}
