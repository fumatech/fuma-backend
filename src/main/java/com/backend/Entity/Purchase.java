package com.backend.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vendor;
    private String referenceNumber;
    private String status;
    private String addedBy;
    private Date purchaseDate;
    private String location;
    private String payTermNumber;
    private String payTermType;

    private String file;

    // List of product items (one-to-many relationship)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_id")
    private List<PurchaseItem> purchaseItems;

    private Long totalItems;
    private BigDecimal netTotalAmount;
    private String discountType;
    private BigDecimal discountAmount;
    private String purchaseTax;
    private BigDecimal taxAmount;
    private String additionalNotes;

    @ManyToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "purchase_payment_method_id")
    private PurchasePaymentMethod purchasePaymentMethod;

    @ManyToOne(cascade = CascadeType.ALL) 
	@JoinColumn(name = "shipping_details_id")
	private ShippingDetails shippingDetails;

    // Getters and Setters for all fields   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    
    

    public String getPayTermNumber() {
		return payTermNumber;
	}

	public void setPayTermNumber(String payTermNumber) {
		this.payTermNumber = payTermNumber;
	}

	public String getPayTermType() {
		return payTermType;
	}

	public void setPayTermType(String payTermType) {
		this.payTermType = payTermType;
	}

	public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getNetTotalAmount() {
        return netTotalAmount;
    }

    public void setNetTotalAmount(BigDecimal netTotalAmount) {
        this.netTotalAmount = netTotalAmount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPurchaseTax() {
        return purchaseTax;
    }

    public void setPurchaseTax(String purchaseTax) {
        this.purchaseTax = purchaseTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public PurchasePaymentMethod getPurchasePaymentMethod() {
        return purchasePaymentMethod;
    }

    public void setPurchasePaymentMethod(PurchasePaymentMethod purchasePaymentMethod) {
        this.purchasePaymentMethod = purchasePaymentMethod;
    }

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }
}
