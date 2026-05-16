package com.backend.Entity.warehouse_transfer;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_transfer_dispatch")
public class WarehouseTransferDispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", nullable = false)
    private WarehouseTransferMaster transfer;

    private String vehicleDetails;
    private String driverDetails;
    private String dispatchNotes;

    @Column(nullable = false)
    private Integer dispatchQuantity;

    @Column(nullable = false)
    private LocalDateTime dispatchDateTime;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WarehouseTransferMaster getTransfer() {
        return transfer;
    }

    public void setTransfer(WarehouseTransferMaster transfer) {
        this.transfer = transfer;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public String getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(String driverDetails) {
        this.driverDetails = driverDetails;
    }

    public String getDispatchNotes() {
        return dispatchNotes;
    }

    public void setDispatchNotes(String dispatchNotes) {
        this.dispatchNotes = dispatchNotes;
    }

    public Integer getDispatchQuantity() {
        return dispatchQuantity;
    }

    public void setDispatchQuantity(Integer dispatchQuantity) {
        this.dispatchQuantity = dispatchQuantity;
    }

    public LocalDateTime getDispatchDateTime() {
        return dispatchDateTime;
    }

    public void setDispatchDateTime(LocalDateTime dispatchDateTime) {
        this.dispatchDateTime = dispatchDateTime;
    }
}
