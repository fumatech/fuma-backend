package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class SaleSoOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderId;

	private String status;

	private String orderRefernceNumber;

	private String franchise;

	private Long franchiseId;

	private Long customerId;

	private Long warehouseId;

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

	// List of product items (one-to-many relationship)
	@OneToMany(mappedBy = "saleSoOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SaleSoItem> saleSoItem;

	// // List of product items (one-to-many relationship)
	// @OneToMany(mappedBy = "saleSoOrder", cascade = CascadeType.ALL, orphanRemoval
	// = true)
	// private List<Transaction> transaction;

	// List of product items (one-to-many relationship)
	@OneToMany(mappedBy = "saleSoOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ShippingSoDetails> shippingSoDetails;

	@OneToMany(mappedBy = "saleSoOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<StockTransaction> stockTransactions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getOrderRefernceNumber() {
		return orderRefernceNumber;
	}

	public void setOrderRefernceNumber(String orderRefernceNumber) {
		this.orderRefernceNumber = orderRefernceNumber;
	}

	public String getFranchise() {
		return franchise;
	}

	public void setFranchise(String franchise) {
		this.franchise = franchise;
	}

	public Long getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(Long franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
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

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Long getTotalSaleItems() {
		return totalSaleItems;
	}

	public void setTotalSaleItems(Long totalSaleItems) {
		this.totalSaleItems = totalSaleItems;
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

	public List<SaleSoItem> getSaleSoItem() {
		return saleSoItem;
	}

	public void setSaleSoItem(List<SaleSoItem> saleSoItem) {
		this.saleSoItem = saleSoItem;
	}

	public List<ShippingSoDetails> getShippingSoDetails() {
		return shippingSoDetails;
	}

	public void setShippingSoDetails(List<ShippingSoDetails> shippingSoDetails) {
		this.shippingSoDetails = shippingSoDetails;
	}

	public List<StockTransaction> getStockTransactions() {
		return stockTransactions;
	}

	public void setStockTransactions(List<StockTransaction> stockTransactions) {
		this.stockTransactions = stockTransactions;
	}

}
