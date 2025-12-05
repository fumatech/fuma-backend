package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sale_payment_methods")
public class SalePaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private PaymentType method; // e.g., Cheque, Bank Transfer, Card, Cash
	
	private Date paidOn;
	private BigDecimal amount;
	private String account; // Account number or details, if applicable
	private String note; // Any additional notes

	// Fields specific to each payment method
	private String chequeNumber; // Used for Cheque payments
	private String bankAccountNumber; // Used for Bank Transfer payments
	private String cutomTransactionNo;// used for custom payments

	// Card specific fields
	private String cardType; // Debit Card, Credit Card
	private String cardNumber; // Card number
	private String cardHolderName; // Cardholder's name
	private Date cardExpiryDate; // Expiry date of the card
	private Long cardSecurity;

	private String cashDetails; // Used for Cash payments

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentType getMethod() {
		return method;
	}

	public void setMethod(PaymentType method) {
		this.method = method;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getCutomTransactionNo() {
		return cutomTransactionNo;
	}

	public void setCutomTransactionNo(String cutomTransactionNo) {
		this.cutomTransactionNo = cutomTransactionNo;
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

	public Date getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(Date cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
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

	// Enum for Payment Method Types
	public enum PaymentType {
		CHEQUE, BANK_TRANSFER, CARD, CASH
	}

}
