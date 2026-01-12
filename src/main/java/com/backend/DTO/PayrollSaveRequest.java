package com.backend.DTO;

import java.sql.Date;
import java.util.List;

public class PayrollSaveRequest {
	private Long payrollId;
	private String location;
	private String monthYear;
	private String addedBy;
	private Date createdAt;
	private Long status;
	private List<EmployeePayrollDTO> employeePayrolls;

	public Long getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(Long payrollId) {
		this.payrollId = payrollId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public List<EmployeePayrollDTO> getEmployeePayrolls() {
		return employeePayrolls;
	}

	public void setEmployeePayrolls(List<EmployeePayrollDTO> employeePayrolls) {
		this.employeePayrolls = employeePayrolls;
	}

}
