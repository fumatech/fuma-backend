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

public class WarrantyClaimItems {

	
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
	    @JoinColumn(name = "warranty_claim_id") 
	    @JsonBackReference
	    private WarrantyClaim warrantyClaim;

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

		public WarrantyClaim getWarrantyClaim() {
			return warrantyClaim;
		}

		public void setWarrantyClaim(WarrantyClaim warrantyClaim) {
			this.warrantyClaim = warrantyClaim;
		}
 
	    

}
