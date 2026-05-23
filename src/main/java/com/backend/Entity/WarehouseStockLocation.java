package com.backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_stock_location", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"warehouseId", "productId", "productVariationId", "rackId", "binId"})
}, indexes = {
    @Index(name = "idx_wsl_warehouse", columnList = "warehouseId"),
    @Index(name = "idx_wsl_product", columnList = "productId"),
    @Index(name = "idx_wsl_rack", columnList = "rackId"),
    @Index(name = "idx_wsl_bin", columnList = "binId")
})
public class WarehouseStockLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private Long productId;

    private Long productVariationId; // Can be null if no variation

    @Column(nullable = false)
    private Long rackId;

    @Column(nullable = false)
    private Long binId;

    @Column(nullable = false)
    private Long quantity = 0L;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private Long createdBy;

    public WarehouseStockLocation() {}

    public WarehouseStockLocation(Long warehouseId, Long productId, Long productVariationId, Long rackId, Long binId, Long quantity, Long createdBy) {
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.productVariationId = productVariationId;
        this.rackId = rackId;
        this.binId = binId;
        this.quantity = quantity;
        this.createdBy = createdBy;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Long productVariationId) {
        this.productVariationId = productVariationId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getBinId() {
        return binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
