package com.backend.Entity.warehouse_transfer;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouse_transfer_item")
public class WarehouseTransferItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", nullable = false)
    private WarehouseTransferMaster transfer;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer requestedQuantity;

    @Column(nullable = false)
    private Integer dispatchedQuantity = 0;

    @Column(nullable = false)
    private Integer receivedQuantity = 0;

    @Column(nullable = false)
    private Integer damagedQuantity = 0;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getDispatchedQuantity() {
        return dispatchedQuantity;
    }

    public void setDispatchedQuantity(Integer dispatchedQuantity) {
        this.dispatchedQuantity = dispatchedQuantity;
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
}
