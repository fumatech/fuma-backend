package com.backend.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PayComponentBulkRequest {

	private String description;
	private String type;
	private String amountType;
	private BigDecimal amount;
	private LocalDateTime applicableDate;

	private List<Long> employeeIds;

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

	public List<Long> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<Long> employeeIds) {
		this.employeeIds = employeeIds;
	}

}
