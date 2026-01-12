package com.backend.DTO;

import java.sql.Date;
import java.util.List;

public class EmployeePayrollViewDTO {
	private Long employeeId;
	private String payrollName;
	private String addedBy;
	private Date createdAt;
	private Long payrollId;
	private int month;
	private int year;
	private Double basic;
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

	public String getPayrollName() {
		return payrollName;
	}

	public void setPayrollName(String payrollName) {
		this.payrollName = payrollName;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(Long payrollId) {
		this.payrollId = payrollId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

}
