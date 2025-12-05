package com.backend.Entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ShippingSoDetails {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String shippingDetails;
	    private BigDecimal shippingCharges;

	   
	    @ElementCollection
	    @CollectionTable(name = "additional_So_expenses", joinColumns = @JoinColumn(name = "shipping_So_details_id"))
	    private List<String> additionalExpensesName;

	    @ElementCollection
	    @CollectionTable(name = "additional_So_expenses_amount", joinColumns = @JoinColumn(name = "shipping_So_details_id"))
	    private List<BigDecimal> amount;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "sale_So_Order_id") 
	    @JsonBackReference
	    private SaleSoOrder saleSoOrder;


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
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


		public List<String> getAdditionalExpensesName() {
			return additionalExpensesName;
		}


		public void setAdditionalExpensesName(List<String> additionalExpensesName) {
			this.additionalExpensesName = additionalExpensesName;
		}


		public List<BigDecimal> getAmount() {
			return amount;
		}


		public void setAmount(List<BigDecimal> amount) {
			this.amount = amount;
		}


		public SaleSoOrder getSaleSoOrder() {
			return saleSoOrder;
		}


		public void setSaleSoOrder(SaleSoOrder saleSoOrder) {
			this.saleSoOrder = saleSoOrder;
		}


}
