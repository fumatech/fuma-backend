package com.backend.Entity;

import java.time.LocalDateTime;

public class AttendanceRequest {

	private Long employeeId;
	private Long shiftId;

	private LocalDateTime inTime;
	private LocalDateTime outTime;

	private String ipAddress;
	private String inNote;
	private String outNote;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public LocalDateTime getInTime() {
		return inTime;
	}

	public void setInTime(LocalDateTime inTime) {
		this.inTime = inTime;
	}

	public LocalDateTime getOutTime() {
		return outTime;
	}

	public void setOutTime(LocalDateTime outTime) {
		this.outTime = outTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getInNote() {
		return inNote;
	}

	public void setInNote(String inNote) {
		this.inNote = inNote;
	}

	public String getOutNote() {
		return outNote;
	}

	public void setOutNote(String outNote) {
		this.outNote = outNote;
	}

}
