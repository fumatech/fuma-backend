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
public class PayrollEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long payrollId;
	private Long employeeId;
	private Double workDuration;
	private Double unit;
	private Double amountPerUnit;
	private Double basic;
	private Double total;
	private Long leaves;
	private Double workHour;
	private Long attendance;
	private String note;

	@OneToMany(mappedBy = "payrollEmployee", cascade = CascadeType.ALL)
	private List<Transaction> transactions = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(Long payrollId) {
		this.payrollId = payrollId;
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

	public Long getLeaves() {
		return leaves;
	}

	public void setLeaves(Long leaves) {
		this.leaves = leaves;
	}

	public Double getWorkHour() {
		return workHour;
	}

	public void setWorkHour(Double workHour) {
		this.workHour = workHour;
	}

	public Long getAttendance() {
		return attendance;
	}

	public void setAttendance(Long attendance) {
		this.attendance = attendance;
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

}
