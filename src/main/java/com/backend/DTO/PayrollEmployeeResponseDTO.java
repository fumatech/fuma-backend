package com.backend.DTO;

import java.util.List;

public class PayrollEmployeeResponseDTO {
	private Long payrollEmployeeId;
	private Long employeeId;
	private Double workDuration;
	private String unit;
	private Double amountPerUnit;
	private Double basic;
	private Double total;
	private String note;

	private List<AmountDTO> earnings;
	private List<AmountDTO> deductions;
	private List<PayrollSalaryTransactionDTO> transactions;

	public Long getPayrollEmployeeId() {
		return payrollEmployeeId;
	}

	public void setPayrollEmployeeId(Long payrollEmployeeId) {
		this.payrollEmployeeId = payrollEmployeeId;
	}

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getAmountPerUnit() {
		return amountPerUnit;
	}

	public void setAmountPerUnit(Double amountPerUnit) {
		this.amountPerUnit = amountPerUnit;
	}

	public Double getBasic() {
		return basic;
	}

	public void setBasic(Double basic) {
		this.basic = basic;
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

	public List<PayrollSalaryTransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<PayrollSalaryTransactionDTO> transactions) {
		this.transactions = transactions;
	}

}
