package com.backend.DTO;

import java.math.BigDecimal;
import java.sql.Date;

public class SaleSoOrderListDTO {

    private Long id;
    private String orderId;
    private String orderRefernceNumber;
    private String franchise;
    private Long franchiseId;
    private Long customerId;
    private String referenceNumber;
    private String orderedBy;
    private String addedBy;
    private Date orderDate;
    private Date saleDate;
    private Long payTermNumber;
    private String payTermType;
    private String location;
    private Long totalItems;
    private Long totalSaleItems;
    private BigDecimal netTotalAmount;
    private String discountType;
    private BigDecimal discountAmount;
    private String purchaseTax;
    private BigDecimal taxAmount;
    private String additionalNotes;
    private String franchiseName;
    private String city;
    private String state;

    public SaleSoOrderListDTO(Long id, String orderId, String orderRefernceNumber, String franchise, Long franchiseId,
            Long customerId, String referenceNumber, String orderedBy, String addedBy, Date orderDate, Date saleDate,
            Long payTermNumber, String payTermType, String location, Long totalItems, Long totalSaleItems,
            BigDecimal netTotalAmount, String discountType, BigDecimal discountAmount, String purchaseTax,
            BigDecimal taxAmount, String additionalNotes, String franchiseName, String city, String state) {
        this.id = id;
        this.orderId = orderId;
        this.orderRefernceNumber = orderRefernceNumber;
        this.franchise = franchise;
        this.franchiseId = franchiseId;
        this.customerId = customerId;
        this.referenceNumber = referenceNumber;
        this.orderedBy = orderedBy;
        this.addedBy = addedBy;
        this.orderDate = orderDate;
        this.saleDate = saleDate;
        this.payTermNumber = payTermNumber;
        this.payTermType = payTermType;
        this.location = location;
        this.totalItems = totalItems;
        this.totalSaleItems = totalSaleItems;
        this.netTotalAmount = netTotalAmount;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.purchaseTax = purchaseTax;
        this.taxAmount = taxAmount;
        this.additionalNotes = additionalNotes;
        this.franchiseName = franchiseName;
        this.city = city;
        this.state = state;
    }

    public Long getId() { return id; }
    public String getOrderId() { return orderId; }
    public String getOrderRefernceNumber() { return orderRefernceNumber; }
    public String getFranchise() { return franchise; }
    public Long getFranchiseId() { return franchiseId; }
    public Long getCustomerId() { return customerId; }
    public String getReferenceNumber() { return referenceNumber; }
    public String getOrderedBy() { return orderedBy; }
    public String getAddedBy() { return addedBy; }
    public Date getOrderDate() { return orderDate; }
    public Date getSaleDate() { return saleDate; }
    public Long getPayTermNumber() { return payTermNumber; }
    public String getPayTermType() { return payTermType; }
    public String getLocation() { return location; }
    public Long getTotalItems() { return totalItems; }
    public Long getTotalSaleItems() { return totalSaleItems; }
    public BigDecimal getNetTotalAmount() { return netTotalAmount; }
    public String getDiscountType() { return discountType; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public String getPurchaseTax() { return purchaseTax; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public String getAdditionalNotes() { return additionalNotes; }
    public String getFranchiseName() { return franchiseName; }
    public String getCity() { return city; }
    public String getState() { return state; }
}