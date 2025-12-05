package com.backend.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Sale {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String customer;
	
	private Long payTermNumber;
	
	private String payTermType;
	
	private String saleDate;
	
	private String status;
	
	private String invoiceScheme;
	
	private Long invoiceNo;
	
	private String discountType;
	
	private BigDecimal discountAmount;
	
	private String orderTax;
	
	private BigDecimal taxAmount;
	
	private String saleNotes;
	
	
	private String shippingDetails;
	
	private BigDecimal shippingCharges;
	
	private String shippingStatus;
	
	private String deliveredTo;
	
	private String deliveryPerson;
	
	 @ManyToOne
	    @JoinColumn(name = "sale_payment_method_id")
	    private SalePaymentMethod salePaymentMethod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Long getPayTermNumber() {
		return payTermNumber;
	}

	public void setPayTermNumber(Long payTermNumber) {
		this.payTermNumber = payTermNumber;
	}

	public String getPayTermType() {
		return payTermType;
	}

	public void setPayTermType(String payTermType) {
		this.payTermType = payTermType;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvoiceScheme() {
		return invoiceScheme;
	}

	public void setInvoiceScheme(String invoiceScheme) {
		this.invoiceScheme = invoiceScheme;
	}

	public Long getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getOrderTax() {
		return orderTax;
	}

	public void setOrderTax(String orderTax) {
		this.orderTax = orderTax;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getSaleNotes() {
		return saleNotes;
	}

	public void setSaleNotes(String saleNotes) {
		this.saleNotes = saleNotes;
	}

	public String getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(String shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public BigDecimal getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(BigDecimal shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getDeliveredTo() {
		return deliveredTo;
	}

	public void setDeliveredTo(String deliveredTo) {
		this.deliveredTo = deliveredTo;
	}

	public String getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(String deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	public SalePaymentMethod getSalePaymentMethod() {
		return salePaymentMethod;
	}

	public void setSalePaymentMethod(SalePaymentMethod salePaymentMethod) {
		this.salePaymentMethod = salePaymentMethod;
	}






	
	


	
	







	
	

}
