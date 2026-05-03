package com.backend.DTO;

import java.math.BigDecimal;

public class ProductBarcodeResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private String barcode;
    private String sku;

    public ProductBarcodeResponse(Long id, String name, BigDecimal price, String barcode, String sku) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.sku = sku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
