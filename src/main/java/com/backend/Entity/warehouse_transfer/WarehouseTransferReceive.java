package com.backend.Entity.warehouse_transfer;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_transfer_receive")
public class WarehouseTransferReceive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", nullable = false)
    private WarehouseTransferMaster transfer;

    @Column(nullable = false)
    private Integer receivedQuantity;

    @Column(nullable = false)
    private Integer damagedQuantity;

    private String receiveRemarks;
    private String receiverDetails;

    @Column(nullable = false)
    private LocalDateTime receiveDateTime;

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

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public Integer getDamagedQuantity() {
        return damagedQuantity;
    }

    public void setDamagedQuantity(Integer damagedQuantity) {
        this.damagedQuantity = damagedQuantity;
    }

    public String getReceiveRemarks() {
        return receiveRemarks;
    }

    public void setReceiveRemarks(String receiveRemarks) {
        this.receiveRemarks = receiveRemarks;
    }

    public String getReceiverDetails() {
        return receiverDetails;
    }

    public void setReceiverDetails(String receiverDetails) {
        this.receiverDetails = receiverDetails;
    }

    public LocalDateTime getReceiveDateTime() {
        return receiveDateTime;
    }

    public void setReceiveDateTime(LocalDateTime receiveDateTime) {
        this.receiveDateTime = receiveDateTime;
    }
}
