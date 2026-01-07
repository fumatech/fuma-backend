package com.backend.DTO;

import java.util.List;

public class EmployeePayrollDTO {
	private Long employeeId;
	private Double workDuration;
	private Double unit;
	private Double amountPerUnit;
	private Double total;
	private String note;
	private List<AmountDTO> earnings;
	private List<AmountDTO> deductions;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Double getWorkDuration() {
		return workDuration;
	}

	public void setWorkDuration(Double workDuration) {
		this.workDuration = workDuration;
	}

	public Double getUnit() {
		return unit;
	}

	public void setUnit(Double unit) {
		this.unit = unit;
	}

	public Double getAmountPerUnit() {
		return amountPerUnit;
	}

	public void setAmountPerUnit(Double amountPerUnit) {
		this.amountPerUnit = amountPerUnit;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<AmountDTO> getEarnings() {
		return earnings;
	}

	public void setEarnings(List<AmountDTO> earnings) {
		this.earnings = earnings;
	}

	public List<AmountDTO> getDeductions() {
		return deductions;
	}

	public void setDeductions(List<AmountDTO> deductions) {
		this.deductions = deductions;
	}

}
