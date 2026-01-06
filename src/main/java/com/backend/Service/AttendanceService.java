package com.backend.Service;

import java.util.List;

import com.backend.Entity.Attendance;
import com.backend.Entity.AttendanceBulkRequest;

public interface AttendanceService {
	Attendance saveAttendance(Attendance attendance);

	void saveBulkAttendance(AttendanceBulkRequest request);

	List<Attendance> getAllAttendance();

	Attendance getAttendanceById(Long id);

	Attendance updateAttendance(Long id, Attendance attendance);

	void deleteAttendance(Long id);

	void deleteAllAttendance();

	Attendance getTodayAttendance(Long employeeId);

}
