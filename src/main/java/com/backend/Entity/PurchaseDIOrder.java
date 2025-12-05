package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
public class PurchaseDIOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String purchaseDIOrderId;

	private Long status;

	private String vendor;

	private String addedBy;

	private String referenceNumber;

	private Date orderDate;

	private Long payTermNumber;

	private String payTermType;

	private String location;

	private String file;

	private BigDecimal netTotalAmount;

	private String discountType;

	private BigDecimal discountAmount;

	private Long purchaseTax;

	private BigDecimal taxAmount;

	private Long totalItems;

	private String additionalNotes;

	@OneToMany(mappedBy = "purchaseDIOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseDIItem> purchaseDIItem;

	@OneToMany(mappedBy = "purchaseDIOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShippingDIDetails> shippingDIDetails;

	@OneToMany(mappedBy = "purchaseDIOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockTransaction> stockTransactions;

	@Transient
	private com.backend.Service.IdGenerator idGenerator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPurchaseDIOrderId() {
		return purchaseDIOrderId;
	}

	public void setPurchaseDIOrderId(String purchaseDIOrderId) {
		this.purchaseDIOrderId = purchaseDIOrderId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Long getPayTermNumber() {
		return payTermNumber;
	}

	public void setPayTermNumber(Long payTermNumber) {
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

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Long getPurchaseTax() {
		return purchaseTax;
	}

	public void setPurchaseTax(Long purchaseTax) {
		this.purchaseTax = purchaseTax;
	}

	public BigDecimal getNetTotalAmount() {
		return netTotalAmount;
	}

	public void setNetTotalAmount(BigDecimal netTotalAmount) {
		this.netTotalAmount = netTotalAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public List<PurchaseDIItem> getPurchaseDIItem() {
		return purchaseDIItem;
	}

	public void setPurchaseDIItem(List<PurchaseDIItem> purchaseDIItem) {
		this.purchaseDIItem = purchaseDIItem;
	}

	public List<ShippingDIDetails> getShippingDIDetails() {
		return shippingDIDetails;
	}

	public void setShippingDIDetails(List<ShippingDIDetails> shippingDIDetails) {
		this.shippingDIDetails = shippingDIDetails;
	}

	public void setIdGenerator(com.backend.Service.IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@PrePersist
	private void generatePurchaseDIOrderId() {
		if (this.purchaseDIOrderId == null && idGenerator != null) {
			this.purchaseDIOrderId = idGenerator.generatePurchaseDIOrderId();
		}
	}

	public List<StockTransaction> getStockTransactions() {
		return stockTransactions;
	}

	public void setStockTransactions(List<StockTransaction> stockTransactions) {
		this.stockTransactions = stockTransactions;
	}

}
