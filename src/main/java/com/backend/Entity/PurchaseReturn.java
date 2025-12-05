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
public class PurchaseReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String purchaseReturnId;

	private Long status;

	private String vendor;

	private String addedBy;

	private Long paymentStatus;

	private String referenceNumber;

	private String invoiceNumber;

	private Date orderDate;

	private String receipt;

	private Long totalItems;

	private Long purchaseTax;

	private BigDecimal totalAmount;
	
    private BigDecimal totalTax;

	private Long totalShippedItems;

	private String additionalNotes;

	@OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseReturnItems> purchaseReturnItems;

	@OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockTransaction> stockTransactions;

	@Transient // Mark this as not persisted in the database
	private com.backend.Service.IdGenerator idGenerator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPurchaseReturnId() {
		return purchaseReturnId;
	}

	public void setPurchaseReturnId(String purchaseReturnId) {
		this.purchaseReturnId = purchaseReturnId;
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

	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Long getPurchaseTax() {
		return purchaseTax;
	}

	public void setPurchaseTax(Long purchaseTax) {
		this.purchaseTax = purchaseTax;
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

	public List<PurchaseReturnItems> getPurchaseReturnItems() {
		return purchaseReturnItems;
	}

	public void setPurchaseReturnItems(List<PurchaseReturnItems> purchaseReturnItems) {
		this.purchaseReturnItems = purchaseReturnItems;
	}

	public com.backend.Service.IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(com.backend.Service.IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@PrePersist
	private void generateIds() {
		if (this.purchaseReturnId == null && idGenerator != null) {
			this.purchaseReturnId = idGenerator.generatePurchaseReturnId();
		}
		if (this.invoiceNumber == null && idGenerator != null) {
			this.invoiceNumber = idGenerator.generatePurchaseReturnInvoiceNumber();
		}
	}

	public List<StockTransaction> getStockTransactions() {
		return stockTransactions;
	}

	public void setStockTransactions(List<StockTransaction> stockTransactions) {
		this.stockTransactions = stockTransactions;
	}

}
