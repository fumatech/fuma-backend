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
public class FranchisePurchaseReturnItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;

	private String productName;

	private String productSku;

	private String productVariationId;

	private String productVariationName;

	private Long quantity;

	private Long updatedQuantity;

	private BigDecimal unitPrice;

	private BigDecimal subTotal;

	private Long tax;

	@ManyToOne
	@JoinColumn(name = "franchise_purchase_return_id")
	@JsonBackReference
	private FranchisePurchaseReturn franchisePurchaseReturn;

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

	public String getProductVariationId() {
		return productVariationId;
	}

	public void setProductVariationId(String productVariationId) {
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

	public Long getUpdatedQuantity() {
		return updatedQuantity;
	}

	public void setUpdatedQuantity(Long updatedQuantity) {
		this.updatedQuantity = updatedQuantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public Long getTax() {
		return tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public FranchisePurchaseReturn getFranchisePurchaseReturn() {
		return franchisePurchaseReturn;
	}

	public void setFranchisePurchaseReturn(FranchisePurchaseReturn franchisePurchaseReturn) {
		this.franchisePurchaseReturn = franchisePurchaseReturn;
	}

}
