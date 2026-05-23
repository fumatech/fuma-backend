package com.backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_bin", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"rackId", "binCode"})
}, indexes = {
    @Index(name = "idx_bin_rack", columnList = "rackId")
})
public class WarehouseBin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long rackId;

    @Column(nullable = false, length = 50)
    private String binCode;

    @Column(nullable = false)
    private Double capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BinStatus status = BinStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private Long createdBy;

    public WarehouseBin() {}

    public WarehouseBin(Long rackId, String binCode, Double capacity, BinStatus status, Long createdBy) {
        this.rackId = rackId;
        this.binCode = binCode;
        this.capacity = capacity;
        this.status = status;
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

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public BinStatus getStatus() {
        return status;
    }

    public void setStatus(BinStatus status) {
        this.status = status;
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
