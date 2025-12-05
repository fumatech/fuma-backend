package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long productId;
	
	private Long variationId;
	
	private Long stockQuantity;

	
	private Long totalStock;
	
	private BigDecimal unitCostBeforeTax;
	
	private BigDecimal subTotalBeforeTax;

	private Date date;
	
	private String note;

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

	

	public Long getVariationId() {
		return variationId;
	}

	public void setVariationId(Long variationId) {
		this.variationId = variationId;
	}
	
	

	public Long getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Long getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Long totalStock) {
		this.totalStock = totalStock;
	}

	public BigDecimal getUnitCostBeforeTax() {
		return unitCostBeforeTax;
	}

	public void setUnitCostBeforeTax(BigDecimal unitCostBeforeTax) {
		this.unitCostBeforeTax = unitCostBeforeTax;
	}

	public BigDecimal getSubTotalBeforeTax() {
		return subTotalBeforeTax;
	}

	public void setSubTotalBeforeTax(BigDecimal subTotalBeforeTax) {
		this.subTotalBeforeTax = subTotalBeforeTax;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	
}
