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
public class FranchiseWarrantyClaimItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;
	private String productName;
	private String productSku;
	private Long productVariationId;
	private String productVariationName;
	private Long quantity;
	private Long updatedQuantity;
	private BigDecimal lineTotal;
	private BigDecimal unitSellingPrice;

	@ManyToOne
	@JoinColumn(name = "franchise_warranty_claim_id")
	@JsonBackReference
	private FranchiseWarrantyClaim franchiseWarrantyClaim;

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

	public Long getUpdatedQuantity() {
		return updatedQuantity;
	}

	public void setUpdatedQuantity(Long updatedQuantity) {
		this.updatedQuantity = updatedQuantity;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}

	public BigDecimal getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(BigDecimal unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public FranchiseWarrantyClaim getFranchiseWarrantyClaim() {
		return franchiseWarrantyClaim;
	}

	public void setFranchiseWarrantyClaim(FranchiseWarrantyClaim franchiseWarrantyClaim) {
		this.franchiseWarrantyClaim = franchiseWarrantyClaim;
	}

}
