package com.backend.Entity;

import java.time.LocalDate;
import java.util.List;

public class AttendanceBulkRequest {
	private LocalDate attendanceDate;
	private List<AttendanceRequest> records;

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public List<AttendanceRequest> getRecords() {
		return records;
	}

	public void setRecords(List<AttendanceRequest> records) {
		this.records = records;
	}

}
