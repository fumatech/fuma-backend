package com.backend.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.Attendance;
import com.backend.Entity.AttendanceBulkRequest;
import com.backend.Service.AttendanceService;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com",
		"https://fusionmastertech.com" }, allowCredentials = "true")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	// ✅ SAVE SINGLE
	@PostMapping("/save")
	public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
		return ResponseEntity.ok(attendanceService.saveAttendance(attendance));
	}

	// ✅ SAVE BULK
	@PostMapping("/bulk")
	public ResponseEntity<String> saveBulkAttendance(@RequestBody AttendanceBulkRequest request) {
		attendanceService.saveBulkAttendance(request);
		return ResponseEntity.ok("Bulk attendance saved");
	}

	// ✅ UPDATE ATTENDANCE
	@PutMapping("/update/{id}")
	public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
		return ResponseEntity.ok(attendanceService.updateAttendance(id, attendance));
	}

	// ✅ GET ALL
	@GetMapping("/getall")
	public List<Attendance> getAllAttendance() {
		return attendanceService.getAllAttendance();
	}

	// ✅ GET BY ID
	@GetMapping("/get/{id}")
	public Attendance getAttendanceById(@PathVariable Long id) {
		return attendanceService.getAttendanceById(id);
	}

	// ✅ DELETE BY ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
		attendanceService.deleteAttendance(id);
		return ResponseEntity.ok("Attendance deleted");
	}

	// ✅ DELETE ALL
	@DeleteMapping("/deleteall")
	public ResponseEntity<String> deleteAllAttendance() {
		attendanceService.deleteAllAttendance();
		return ResponseEntity.ok("All attendance deleted");
	}

	// ✅ Check if user is clocked in
	@GetMapping("/current-status/{employeeId}")
	public ResponseEntity<?> getCurrentStatus(@PathVariable Long employeeId) {
		Attendance attendance = attendanceService.getTodayAttendance(employeeId);
		if (attendance != null && attendance.getInTime() != null && attendance.getOutTime() == null) {
			return ResponseEntity.ok(Map.of("clockedIn", true, "attendanceId", attendance.getId()));
		}
		return ResponseEntity.ok(Map.of("clockedIn", false));
	}

	// ✅ Clock In
	@PostMapping("/clock-in")
	public ResponseEntity<?> clockIn(@RequestBody Attendance attendance) {
		attendance.setAttendanceDate(LocalDate.now());
		attendance.setInTime(LocalDateTime.now());
		Attendance saved = attendanceService.saveAttendance(attendance);
		return ResponseEntity.ok(Map.of("id", saved.getId()));
	}

	// ✅ Clock Out
	@PostMapping("/clock-out/{id}")
	public ResponseEntity<?> clockOut(@PathVariable Long id, @RequestBody Map<String, String> body) {
		Attendance attendance = attendanceService.getAttendanceById(id);
		attendance.setOutTime(LocalDateTime.now());
		attendance.setOutNote(body.get("note")); // Optional
		attendanceService.updateAttendance(id, attendance);
		return ResponseEntity.ok(Map.of("success", true));
	}

}
