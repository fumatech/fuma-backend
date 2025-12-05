package com.backend.Entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "product_variations")
public class ProductVariations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String variationName;
    
    private String subSku;
    
    private String variationValue;
    
    private BigDecimal defaultPurchasePriceExcTax;

    private BigDecimal defaultPurchasePriceIncTax;
    
    private BigDecimal defaultSellingPrice;

    private BigDecimal margin;

    private String variationProductImages;
    
    @Column(columnDefinition = "TEXT", nullable = true)
    private String comboVariations;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id") // Foreign key column name
    private Product product;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public String getSubSku() {
        return subSku;
    }

    public void setSubSku(String subSku) {
        this.subSku = subSku;
    }

    public String getVariationValue() {
        return variationValue;
    }

    public void setVariationValue(String variationValue) {
        this.variationValue = variationValue;
    }

   

    public BigDecimal getDefaultPurchasePriceExcTax() {
		return defaultPurchasePriceExcTax;
	}

	public void setDefaultPurchasePriceExcTax(BigDecimal defaultPurchasePriceExcTax) {
		this.defaultPurchasePriceExcTax = defaultPurchasePriceExcTax;
	}

	public BigDecimal getDefaultPurchasePriceIncTax() {
		return defaultPurchasePriceIncTax;
	}

	public void setDefaultPurchasePriceIncTax(BigDecimal defaultPurchasePriceIncTax) {
		this.defaultPurchasePriceIncTax = defaultPurchasePriceIncTax;
	}

	public BigDecimal getDefaultSellingPrice() {
        return defaultSellingPrice;
    }

    public void setDefaultSellingPrice(BigDecimal defaultSellingPrice) {
        this.defaultSellingPrice = defaultSellingPrice;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public String getVariationProductImages() {
        return variationProductImages;
    }

    public void setVariationProductImages(String variationProductImages) {
        this.variationProductImages = variationProductImages;
    }
    
    
    

    public String getComboVariations() {
		return comboVariations;
	}

	public void setComboVariations(String comboVariations) {
		this.comboVariations = comboVariations;
	}

	public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
}
