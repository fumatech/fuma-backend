package com.backend.Service;

import java.util.List;

import com.backend.Entity.Attendance;

public interface AttendanceService {

	Attendance saveAttendance(Attendance attendance);

	List<Attendance> getAllAttendances();

	Attendance updateAttendance(Long id, Attendance updatedAttendance);

	Attendance getAttendanceById(Long id);

	void deleteAttendanceById(Long id);

}
