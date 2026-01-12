package com.backend.DTO;

import java.sql.Date;
import java.util.List;

public class PayrollResponseDTO {
	private Long id;
	private String payrollName;
	private Long location;
	private int month;
	private int year;
	private String addedBy;
	private Date createdAt;
	private Integer status;
	private List<PayrollEmployeeResponseDTO> employees;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayrollName() {
		return payrollName;
	}

	public void setPayrollName(String payrollName) {
		this.payrollName = payrollName;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
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

	public Integer getStatus() {
		return status;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<PayrollEmployeeResponseDTO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<PayrollEmployeeResponseDTO> employees) {
		this.employees = employees;
	}

}
