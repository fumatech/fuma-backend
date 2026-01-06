package com.backend.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Attendance;
import com.backend.Entity.AttendanceBulkRequest;
import com.backend.Entity.AttendanceRequest;
import com.backend.Entity.AttendanceStatus;
import com.backend.Repository.AttendanceRepo;
import com.backend.Service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceRepo attendanceRepo;

	// 🔹 SINGLE SAVE
	@Override
	public Attendance saveAttendance(Attendance attendance) {
		attendance.setStatus(calculateStatus(attendance.getInTime(), attendance.getOutTime()));
		return attendanceRepo.save(attendance);
	}

	// 🔹 BULK SAVE
	@Override
	public void saveBulkAttendance(AttendanceBulkRequest request) {

		List<Attendance> list = new ArrayList<>();

		for (AttendanceRequest r : request.getRecords()) {

			Attendance a = new Attendance();
			a.setEmployeeId(r.getEmployeeId());
			a.setShiftId(r.getShiftId());
			a.setAttendanceDate(request.getAttendanceDate());
			a.setInTime(r.getInTime());
			a.setOutTime(r.getOutTime());
			a.setIpAddress(r.getIpAddress());
			a.setInNote(r.getInNote());
			a.setOutNote(r.getOutNote());

			a.setStatus(calculateStatus(r.getInTime(), r.getOutTime()));
			list.add(a);
		}

		attendanceRepo.saveAll(list);
	}

	// 🔹 GET ALL
	@Override
	public List<Attendance> getAllAttendance() {
		return attendanceRepo.findAll();
	}

	// 🔹 GET BY ID
	@Override
	public Attendance getAttendanceById(Long id) {
		return attendanceRepo.findById(id).orElseThrow(() -> new RuntimeException("Attendance not found"));
	}

	// 🔹 DELETE BY ID
	@Override
	public void deleteAttendance(Long id) {
		attendanceRepo.deleteById(id);
	}

	// 🔹 DELETE ALL
	@Override
	public void deleteAllAttendance() {
		attendanceRepo.deleteAll();
	}

	// 🔥 STATUS LOGIC
	private AttendanceStatus calculateStatus(LocalDateTime inTime, LocalDateTime outTime) {
		if (inTime == null && outTime == null) {
			return AttendanceStatus.ABSENT;
		}
		if (inTime != null && outTime == null) {
			return AttendanceStatus.HALF_DAY;
		}
		return AttendanceStatus.PRESENT;
	}

	@Override
	public Attendance updateAttendance(Long id, Attendance updatedAttendance) {

		Attendance existing = attendanceRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Attendance not found"));

		existing.setEmployeeId(updatedAttendance.getEmployeeId());
		existing.setShiftId(updatedAttendance.getShiftId());
		existing.setAttendanceDate(updatedAttendance.getAttendanceDate());
		existing.setInTime(updatedAttendance.getInTime());
		existing.setOutTime(updatedAttendance.getOutTime());
		existing.setIpAddress(updatedAttendance.getIpAddress());
		existing.setInNote(updatedAttendance.getInNote());
		existing.setOutNote(updatedAttendance.getOutNote());

		existing.setStatus(calculateStatus(updatedAttendance.getInTime(), updatedAttendance.getOutTime()));

		return attendanceRepo.save(existing);
	}

	@Override
	public Attendance getTodayAttendance(Long employeeId) {
		LocalDate today = LocalDate.now();
		return attendanceRepo.findByEmployeeIdAndAttendanceDate(employeeId, today).orElse(null);
	}

}
