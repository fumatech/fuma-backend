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
public class FranchisePurchaseReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String franchisePurchaseReturnId;

	private Long status;

	private Long paymentStatus;

	private Long customerId;

	private String vendor;

	private String addedBy;

	private String referenceNumber;

	private String invoiceNumber;

	private Date orderDate;

	private String franchiseId;

	private String location;

	private String file;

	private Long totalItems;

	private Long totalShippedItems;

	private String additionalNotes;

	private BigDecimal netTotalAmount;

	@OneToMany(mappedBy = "franchisePurchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FranchisePurchaseReturnItems> franchisePurchaseReturnItems;

	@OneToMany(mappedBy = "franchisePurchaseReturn", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<StockTransaction> stockTransactions;

	@Transient
	private com.backend.Service.IdGenerator idGenerator;

	@PrePersist
	private void generateIds() {
		if (this.franchisePurchaseReturnId == null && idGenerator != null) {
			this.franchisePurchaseReturnId = idGenerator.generateFranchisePurchaseReturnId();
		}
		if (this.invoiceNumber == null && idGenerator != null) {
			this.invoiceNumber = idGenerator.generateFranchisePurchaseReturnInvoiceNumber();
		}
	}

	public void setIdGenerator(com.backend.Service.IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFranchisePurchaseReturnId() {
		return franchisePurchaseReturnId;
	}

	public void setFranchisePurchaseReturnId(String franchisePurchaseReturnId) {
		this.franchisePurchaseReturnId = franchisePurchaseReturnId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
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

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Long getTotalShippedItems() {
		return totalShippedItems;
	}

	public void setTotalShippedItems(Long totalShippedItems) {
		this.totalShippedItems = totalShippedItems;
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getNetTotalAmount() {
		return netTotalAmount;
	}

	public void setNetTotalAmount(BigDecimal netTotalAmount) {
		this.netTotalAmount = netTotalAmount;
	}

	public List<FranchisePurchaseReturnItems> getFranchisePurchaseReturnItems() {
		return franchisePurchaseReturnItems;
	}

	public void setFranchisePurchaseReturnItems(List<FranchisePurchaseReturnItems> franchisePurchaseReturnItems) {
		this.franchisePurchaseReturnItems = franchisePurchaseReturnItems;
	}

	public List<StockTransaction> getStockTransactions() {
		return stockTransactions;
	}

	public void setStockTransactions(List<StockTransaction> stockTransactions) {
		this.stockTransactions = stockTransactions;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}
