package com.backend.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Attendance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ElementCollection
	private List<Long> employee;
	
	@ElementCollection
	private List<LocalDateTime> inTime;
	
	@ElementCollection
	private List<LocalDateTime> outTime;
	
	@ElementCollection
	private List<String> shift;
	
	@ElementCollection
	private List<String> ipAddress;
	
	@ElementCollection
	private List<String> inNote;
	
	@ElementCollection
	private List<String> outNote;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Long> employee) {
		this.employee = employee;
	}

	public List<LocalDateTime> getInTime() {
		return inTime;
	}

	public void setInTime(List<LocalDateTime> inTime) {
		this.inTime = inTime;
	}

	public List<LocalDateTime> getOutTime() {
		return outTime;
	}

	public void setOutTime(List<LocalDateTime> outTime) {
		this.outTime = outTime;
	}

	public List<String> getShift() {
		return shift;
	}

	public void setShift(List<String> shift) {
		this.shift = shift;
	}

	public List<String> getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<String> getInNote() {
		return inNote;
	}

	public void setInNote(List<String> inNote) {
		this.inNote = inNote;
	}

	public List<String> getOutNote() {
		return outNote;
	}

	public void setOutNote(List<String> outNote) {
		this.outNote = outNote;
	}
	

}
