package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;

public class SellReportDTO {

	private String products;
	private String sku;
	private Long customerId;
	private String franchise;
	private String invoiceNo;
	private Date date;
	private Long quantity;
	private BigDecimal unitPrice;
	private BigDecimal discount;
	private BigDecimal tax;
	private BigDecimal priceIncTax;
	private BigDecimal total;

	public SellReportDTO(String products, String sku, Long customerId, String franchise, String invoiceNo, Date date,
			Long quantity, BigDecimal unitPrice, BigDecimal discount, BigDecimal tax, BigDecimal priceIncTax,
			BigDecimal total) {
		super();
		this.products = products;
		this.sku = sku;
		this.customerId = customerId;
		this.franchise = franchise;
		this.invoiceNo = invoiceNo;
		this.date = date;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.tax = tax;
		this.priceIncTax = priceIncTax;
		this.total = total;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFranchise() {
		return franchise;
	}

	public void setFranchise(String franchise) {
		this.franchise = franchise;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getPriceIncTax() {
		return priceIncTax;
	}

	public void setPriceIncTax(BigDecimal priceIncTax) {
		this.priceIncTax = priceIncTax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
