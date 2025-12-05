package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class SaleDIPaymentMethod {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Enumerated(EnumType.STRING)
	    private PaymentType methodName;  // e.g., Cheque, Bank Transfer, Card, Cash
	    
	    private Date paidOn;
	    private BigDecimal amount;
	    private String paymentAccount;  // Account number or details, if applicable
	    private String paymentNote; // Any additional notes

	    // Fields specific to each payment method
	    private String chequeNumber;  // Used for Cheque payments
	    private String bankAccountNumber;  // Used for Bank Transfer payments

	    // Card specific fields
	    private String cardType;  // Debit Card, Credit Card
	    private String cardNumber;  // Card number
	    private String cardHolderName;  // Cardholder's name
	    private String cardTransactionNumber;  // Cardholder's name
	    
	    private Long cardMonth;  // Cardholder's name

	    private Long cardYear;
	    
	    private Long cardSecurity; //card security

	    private String cashDetails;  // Used for Cash payments
	    

	    @ManyToOne
	    @JoinColumn(name = "sale_DI_Order_id")  
	    @JsonBackReference
	    private SaleDIOrder saleDIOrder;


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public PaymentType getMethodName() {
			return methodName;
		}


		public void setMethodName(PaymentType methodName) {
			this.methodName = methodName;
		}


		public Date getPaidOn() {
			return paidOn;
		}


		public void setPaidOn(Date paidOn) {
			this.paidOn = paidOn;
		}


		public BigDecimal getAmount() {
			return amount;
		}


		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}


		public String getPaymentAccount() {
			return paymentAccount;
		}


		public void setPaymentAccount(String paymentAccount) {
			this.paymentAccount = paymentAccount;
		}


		public String getPaymentNote() {
			return paymentNote;
		}


		public void setPaymentNote(String paymentNote) {
			this.paymentNote = paymentNote;
		}


		public String getChequeNumber() {
			return chequeNumber;
		}


		public void setChequeNumber(String chequeNumber) {
			this.chequeNumber = chequeNumber;
		}


		public String getBankAccountNumber() {
			return bankAccountNumber;
		}


		public void setBankAccountNumber(String bankAccountNumber) {
			this.bankAccountNumber = bankAccountNumber;
		}


		public String getCardType() {
			return cardType;
		}


		public void setCardType(String cardType) {
			this.cardType = cardType;
		}


		public String getCardNumber() {
			return cardNumber;
		}


		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}


		public String getCardHolderName() {
			return cardHolderName;
		}


		public void setCardHolderName(String cardHolderName) {
			this.cardHolderName = cardHolderName;
		}


		public String getCardTransactionNumber() {
			return cardTransactionNumber;
		}


		public void setCardTransactionNumber(String cardTransactionNumber) {
			this.cardTransactionNumber = cardTransactionNumber;
		}


		public Long getCardMonth() {
			return cardMonth;
		}


		public void setCardMonth(Long cardMonth) {
			this.cardMonth = cardMonth;
		}


		public Long getCardYear() {
			return cardYear;
		}


		public void setCardYear(Long cardYear) {
			this.cardYear = cardYear;
		}


		public Long getCardSecurity() {
			return cardSecurity;
		}


		public void setCardSecurity(Long cardSecurity) {
			this.cardSecurity = cardSecurity;
		}


		public String getCashDetails() {
			return cashDetails;
		}


		public void setCashDetails(String cashDetails) {
			this.cashDetails = cashDetails;
		}


		public SaleDIOrder getSaleDIOrder() {
			return saleDIOrder;
		}


		public void setSaleDIOrder(SaleDIOrder saleDIOrder) {
			this.saleDIOrder = saleDIOrder;
		}
		// Enum for Payment Method Types
	    public enum PaymentType {
	        CHEQUE,
	        BANK_TRANSFER,
	        CARD,
	        CASH
	    } 
	    
	    
	    
	    


}
