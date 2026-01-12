package com.backend.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PayComponent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String description;
	private String type;
	private String amountType;
	private BigDecimal amount;
	private LocalDateTime applicableDate;

	@ElementCollection
	private List<Long> employeeId;;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getApplicableDate() {
		return applicableDate;
	}

	public void setApplicableDate(LocalDateTime applicableDate) {
		this.applicableDate = applicableDate;
	}

	public List<Long> getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(List<Long> employeeId) {
		this.employeeId = employeeId;
	}

}
