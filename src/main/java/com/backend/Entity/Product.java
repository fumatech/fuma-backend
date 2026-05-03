package com.backend.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_products_barcode", columnList = "barcode")})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String sku;
    private String barcode;
    private String unit;
    private String brand;
    private Boolean manageStock;
    private Long alertQuantity;
    private String category;
    private String businessLocation;
    private String productImage;
    private String description;
    private String applicableTax;
    private String sellingPriceTaxType;
    private Long status;
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProductVariations> productVariations;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getManageStock() {
        return manageStock;
    }

    public void setManageStock(Boolean manageStock) {
        this.manageStock = manageStock;
    }

    public Long getAlertQuantity() {
        return alertQuantity;
    }

    public void setAlertQuantity(Long alertQuantity) {
        this.alertQuantity = alertQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicableTax() {
        return applicableTax;
    }

    public void setApplicableTax(String applicableTax) {
        this.applicableTax = applicableTax;
    }

    public String getSellingPriceTaxType() {
        return sellingPriceTaxType;
    }

    public void setSellingPriceTaxType(String sellingPriceTaxType) {
        this.sellingPriceTaxType = sellingPriceTaxType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<ProductVariations> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(List<ProductVariations> productVariations) {
        this.productVariations = productVariations;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public enum ProductType {
        SINGLE, VARIABLE, COMBO
    }
}
