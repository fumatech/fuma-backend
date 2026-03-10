package com.backend.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year);

    Map<String, Object> getMonthlyWorkingSummary(Long employeeId, int month, int year);

    List<Map<String, Object>> getAllEmployeesMonthlyWorkingSummary(int month, int year);

    List<Attendance> getAttendanceByDate(LocalDate date);

    List<Attendance> getTodayFaceScans();

}
