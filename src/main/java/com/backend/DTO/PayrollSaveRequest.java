package com.backend.DTO;

import java.util.List;

public class PayrollSaveRequest {
	private String location;
	private String monthYear;
	private Long status;
	private List<EmployeePayrollDTO> employeePayrolls;

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

	public List<EmployeePayrollDTO> getEmployeePayrolls() {
		return employeePayrolls;
	}

	public void setEmployeePayrolls(List<EmployeePayrollDTO> employeePayrolls) {
		this.employeePayrolls = employeePayrolls;
	}

}
