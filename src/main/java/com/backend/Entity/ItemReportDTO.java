package com.backend.Entity;

import java.math.BigDecimal;

public class ItemReportDTO {

	private Long id;
	private String product;
	private String sku;
	private String description;

	private String purchaseDate;
	private BigDecimal purchase;
	private String lotNumber;
	private String supplier;
	private BigDecimal purchasePrice;

	private String sellDate;
	private BigDecimal sale;
	private String customer;
	private String location;
	private Long sellQuantity;
	private BigDecimal selling;
	private BigDecimal subtotal;

	// ---------------------------------------------------------
	// FULL CONSTRUCTOR REQUIRED BY JPQL (16 PARAMETERS)
	// ---------------------------------------------------------
	public ItemReportDTO(Long id, String product, String sku, String description, String purchaseDate,
			BigDecimal purchase, String lotNumber, String supplier, BigDecimal purchasePrice, String sellDate,
			BigDecimal sale, String customer, String location, Long sellQuantity, BigDecimal selling,
			BigDecimal subtotal) {
		this.id = id;
		this.product = product;
		this.sku = sku;
		this.description = description;
		this.purchaseDate = purchaseDate;
		this.purchase = purchase;
		this.lotNumber = lotNumber;
		this.supplier = supplier;
		this.purchasePrice = purchasePrice;
		this.sellDate = sellDate;
		this.sale = sale;
		this.customer = customer;
		this.location = location;
		this.sellQuantity = sellQuantity;
		this.selling = selling;
		this.subtotal = subtotal;
	}

	// ---------------------------------------------------------
	// GETTERS & SETTERS
	// ---------------------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}

	public BigDecimal getSale() {
		return sale;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Long sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public BigDecimal getSelling() {
		return selling;
	}

	public void setSelling(BigDecimal selling) {
		this.selling = selling;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
}
