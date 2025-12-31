package com.backend.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class BusinessLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long isActive;

	private String name;

	private String locationId;

	private String landmark;

	private String city;

	private Long zipCode;

	private String state;

	private String country;

	private Long mobileNumber;

	private Long alternateContactNumber;

	private String email;

	private String website;

	private Long businessCategoryId;

	private Long invoiceSchemeForPosId;

	private Long invoiceSchemeForSaleId;

	private Long invoiceLayoutForPosId;

	private Long invoiceLayoutForSaleId;

	private Long defaultSellingPriceGroupId;

	private String customField1;

	private String customField2;

	private String customField3;

	private String customField4;

	@Lob
	private String featuredProducts;

	@Lob
	private String defaultPaymentAccount;

	private Long printReceiptOnInvoice;

	private Long printerId;

	@Enumerated(EnumType.STRING)
	@Column(name = "receipt_printer_type")
	private ReceiptPrinterType receiptPrinterType;

	public enum ReceiptPrinterType {
		browser, printer
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIsActive() {
		return isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getZipCode() {
		return zipCode;
	}

	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getAlternateContactNumber() {
		return alternateContactNumber;
	}

	public void setAlternateContactNumber(Long alternateContactNumber) {
		this.alternateContactNumber = alternateContactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Long getBusinessCategoryId() {
		return businessCategoryId;
	}

	public void setBusinessCategoryId(Long businessCategoryId) {
		this.businessCategoryId = businessCategoryId;
	}

	public Long getInvoiceSchemeForPosId() {
		return invoiceSchemeForPosId;
	}

	public void setInvoiceSchemeForPosId(Long invoiceSchemeForPosId) {
		this.invoiceSchemeForPosId = invoiceSchemeForPosId;
	}

	public Long getInvoiceSchemeForSaleId() {
		return invoiceSchemeForSaleId;
	}

	public void setInvoiceSchemeForSaleId(Long invoiceSchemeForSaleId) {
		this.invoiceSchemeForSaleId = invoiceSchemeForSaleId;
	}

	public Long getInvoiceLayoutForPosId() {
		return invoiceLayoutForPosId;
	}

	public void setInvoiceLayoutForPosId(Long invoiceLayoutForPosId) {
		this.invoiceLayoutForPosId = invoiceLayoutForPosId;
	}

	public Long getInvoiceLayoutForSaleId() {
		return invoiceLayoutForSaleId;
	}

	public void setInvoiceLayoutForSaleId(Long invoiceLayoutForSaleId) {
		this.invoiceLayoutForSaleId = invoiceLayoutForSaleId;
	}

	public Long getDefaultSellingPriceGroupId() {
		return defaultSellingPriceGroupId;
	}

	public void setDefaultSellingPriceGroupId(Long defaultSellingPriceGroupId) {
		this.defaultSellingPriceGroupId = defaultSellingPriceGroupId;
	}

	public String getCustomField1() {
		return customField1;
	}

	public void setCustomField1(String customField1) {
		this.customField1 = customField1;
	}

	public String getCustomField2() {
		return customField2;
	}

	public void setCustomField2(String customField2) {
		this.customField2 = customField2;
	}

	public String getCustomField3() {
		return customField3;
	}

	public void setCustomField3(String customField3) {
		this.customField3 = customField3;
	}

	public String getCustomField4() {
		return customField4;
	}

	public void setCustomField4(String customField4) {
		this.customField4 = customField4;
	}

	public String getFeaturedProducts() {
		return featuredProducts;
	}

	public void setFeaturedProducts(String featuredProducts) {
		this.featuredProducts = featuredProducts;
	}

	public String getDefaultPaymentAccount() {
		return defaultPaymentAccount;
	}

	public void setDefaultPaymentAccount(String defaultPaymentAccount) {
		this.defaultPaymentAccount = defaultPaymentAccount;
	}

	public Long getPrintReceiptOnInvoice() {
		return printReceiptOnInvoice;
	}

	public void setPrintReceiptOnInvoice(Long printReceiptOnInvoice) {
		this.printReceiptOnInvoice = printReceiptOnInvoice;
	}

	public Long getPrinterId() {
		return printerId;
	}

	public void setPrinterId(Long printerId) {
		this.printerId = printerId;
	}

	public ReceiptPrinterType getReceiptPrinterType() {
		return receiptPrinterType;
	}

	public void setReceiptPrinterType(ReceiptPrinterType receiptPrinterType) {
		this.receiptPrinterType = receiptPrinterType;
	}

}
