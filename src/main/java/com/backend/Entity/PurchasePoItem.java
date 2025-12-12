package com.backend.Entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PurchasePoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;
	private String productName;
	private String productSku;
	private Long productVariationId;
	private String productVariationName;
	private Long quantity;
	private BigDecimal unitCostBeforeDiscount;
	private BigDecimal discountPercent;
	private BigDecimal unitCostAfterDiscount;
	private BigDecimal lineTotal;
	private Long taxRate;
	private BigDecimal taxAmount;

	private BigDecimal profitMargin;
	private BigDecimal unitSellingPrice;

	@ManyToOne
	@JoinColumn(name = "purchase_Po_Order_id")
	@JsonBackReference
	private PurchasePoOrder purchasePoOrder;

	// Getters and Setters for all fields

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public Long getProductVariationId() {
		return productVariationId;
	}

	public void setProductVariationId(Long productVariationId) {
		this.productVariationId = productVariationId;
	}

	public String getProductVariationName() {
		return productVariationName;
	}

	public void setProductVariationName(String productVariationName) {
		this.productVariationName = productVariationName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitCostBeforeDiscount() {
		return unitCostBeforeDiscount;
	}

	public void setUnitCostBeforeDiscount(BigDecimal unitCostBeforeDiscount) {
		this.unitCostBeforeDiscount = unitCostBeforeDiscount;
	}

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public BigDecimal getUnitCostAfterDiscount() {
		return unitCostAfterDiscount;
	}

	public void setUnitCostAfterDiscount(BigDecimal unitCostAfterDiscount) {
		this.unitCostAfterDiscount = unitCostAfterDiscount;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}

	public BigDecimal getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(BigDecimal profitMargin) {
		this.profitMargin = profitMargin;
	}

	public BigDecimal getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public PurchasePoOrder getPurchasePoOrder() {
		return purchasePoOrder;
	}

	public void setPurchasePoOrder(PurchasePoOrder purchasePoOrder) {
		this.purchasePoOrder = purchasePoOrder;
	}

	public Long getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Long taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

}
