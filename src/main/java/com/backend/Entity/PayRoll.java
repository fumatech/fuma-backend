package com.backend.Entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class PayRoll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String location;
    private String monthYear;
    private Long status; // 0=draft, 1=final
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeePayroll> employeePayrolls = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<EmployeePayroll> getEmployeePayrolls() {
		return employeePayrolls;
	}

	public void setEmployeePayrolls(List<EmployeePayroll> employeePayrolls) {
		this.employeePayrolls = employeePayrolls;
	}
    
    
    
}