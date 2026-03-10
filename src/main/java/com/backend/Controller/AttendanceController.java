package com.backend.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
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
import com.backend.Service.FaceEncodingService;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com",
    "https://fusionmastertech.com"}, allowCredentials = "true")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private FaceEncodingService faceEncodingService;

    @PostMapping("/save")
    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.saveAttendance(attendance));
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> saveBulkAttendance(@RequestBody AttendanceBulkRequest request) {
        attendanceService.saveBulkAttendance(request);
        return ResponseEntity.ok("Bulk attendance saved");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, attendance));
    }

    @GetMapping("/getall")
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/get/{id}")
    public Attendance getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok("Attendance deleted");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAllAttendance() {
        attendanceService.deleteAllAttendance();
        return ResponseEntity.ok("All attendance deleted");
    }

    @GetMapping("/current-status/{employeeId}")
    public ResponseEntity<?> getCurrentStatus(@PathVariable Long employeeId) {
        Attendance attendance = attendanceService.getTodayAttendance(employeeId);
        if (attendance != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("clockedIn", attendance.getInTime() != null && attendance.getOutTime() == null);
            result.put("attendanceId", attendance.getId());
            result.put("inTime", attendance.getInTime());
            result.put("outTime", attendance.getOutTime());
            result.put("totalWorkingHours", attendance.getTotalWorkingHours());
            result.put("status", attendance.getStatus());
            result.put("loginMethod", attendance.getLoginMethod());
            result.put("attendanceDate", attendance.getAttendanceDate());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(Map.of("clockedIn", false));
    }

    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn(@RequestBody Attendance attendance) {
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setInTime(LocalDateTime.now());
        attendance.setLoginMethod("MANUAL");
        Attendance saved = attendanceService.saveAttendance(attendance);
        return ResponseEntity.ok(Map.of("id", saved.getId()));
    }

    @PostMapping("/clock-out/{id}")
    public ResponseEntity<?> clockOut(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        attendance.setOutTime(LocalDateTime.now());
        attendance.setOutNote(body.get("note"));
        attendanceService.updateAttendance(id, attendance);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/face-clock-in")
    public ResponseEntity<?> faceClockIn(@RequestBody Map<String, String> body) {
        String faceDescriptor = body.get("faceDescriptor");

        // Match face
        Long matchedEmployeeId = faceEncodingService.matchFace(faceDescriptor);
        if (matchedEmployeeId == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Face not recognized"));
        }

        // Check if already clocked in today
        Attendance existing = attendanceService.getTodayAttendance(matchedEmployeeId);
        if (existing != null && existing.getInTime() != null && existing.getOutTime() == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Already clocked in today",
                    "attendanceId", existing.getId(), "employeeId", matchedEmployeeId));
        }

        // Create attendance record
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(matchedEmployeeId);
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setInTime(LocalDateTime.now());
        attendance.setLoginMethod("FACE_SCAN");
        attendance.setIpAddress(body.get("ipAddress"));
        attendance.setInNote("Face scan clock-in");

        Attendance saved = attendanceService.saveAttendance(attendance);
        return ResponseEntity.ok(Map.of("success", true, "attendanceId", saved.getId(),
                "employeeId", matchedEmployeeId, "message", "Clock-in successful"));
    }

    @PostMapping("/face-clock-out")
    public ResponseEntity<?> faceClockOut(@RequestBody Map<String, String> body) {
        String faceDescriptor = body.get("faceDescriptor");

        // Match face
        Long matchedEmployeeId = faceEncodingService.matchFace(faceDescriptor);
        if (matchedEmployeeId == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Face not recognized"));
        }

        // Find today's attendance
        Attendance attendance = attendanceService.getTodayAttendance(matchedEmployeeId);
        if (attendance == null || attendance.getInTime() == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "No clock-in found for today",
                    "employeeId", matchedEmployeeId));
        }

        if (attendance.getOutTime() != null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Already clocked out today",
                    "employeeId", matchedEmployeeId));
        }

        attendance.setOutTime(LocalDateTime.now());
        attendance.setOutNote("Face scan clock-out");
        attendanceService.updateAttendance(attendance.getId(), attendance);

        return ResponseEntity.ok(Map.of("success", true, "employeeId", matchedEmployeeId,
                "totalWorkingHours", attendance.getTotalWorkingHours() != null ? attendance.getTotalWorkingHours() : Double.valueOf(0),
                "message", "Clock-out successful"));
    }

    //  Get monthly attendance for an employee
    @GetMapping("/monthly/{employeeId}/{month}/{year}")
    public ResponseEntity<?> getMonthlyAttendance(@PathVariable Long employeeId, @PathVariable int month,
            @PathVariable int year) {
        return ResponseEntity.ok(attendanceService.getMonthlyAttendance(employeeId, month, year));
    }

    // Get monthly working summary for an employee
    @GetMapping("/working-summary/{employeeId}/{month}/{year}")
    public ResponseEntity<?> getMonthlyWorkingSummary(@PathVariable Long employeeId, @PathVariable int month,
            @PathVariable int year) {
        return ResponseEntity.ok(attendanceService.getMonthlyWorkingSummary(employeeId, month, year));
    }

    //  Get all employees monthly working summary
    @GetMapping("/working-summary-all/{month}/{year}")
    public ResponseEntity<?> getAllEmployeesMonthlyWorkingSummary(@PathVariable int month, @PathVariable int year) {
        return ResponseEntity.ok(attendanceService.getAllEmployeesMonthlyWorkingSummary(month, year));
    }

    //  Get attendance by date
    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(localDate));
    }

    // Get today's face scan records
    @GetMapping("/today-face-scans")
    public ResponseEntity<List<Attendance>> getTodayFaceScans() {
        return ResponseEntity.ok(attendanceService.getTodayFaceScans());
    }

}
