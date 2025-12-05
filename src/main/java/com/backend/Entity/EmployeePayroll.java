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
public class EmployeePayroll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long employeeId;
    private Double workDuration;
    private Double unit;
    private Double amountPerUnit;
    private Double total;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Earning> earnings = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deduction> deductions = new ArrayList<>();
    
    private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Earning> getEarnings() {
		return earnings;
	}

	public void setEarnings(List<Earning> earnings) {
		this.earnings = earnings;
	}

	public List<Deduction> getDeductions() {
		return deductions;
	}

	public void setDeductions(List<Deduction> deductions) {
		this.deductions = deductions;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
    
    
    
    
}