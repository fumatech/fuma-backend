package com.backend.Controller;

import com.backend.Entity.Attendance;
import com.backend.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	    },
	    allowCredentials = "true"
	)
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Get all attendances
    @GetMapping("/getall")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    // Get attendance by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        return attendance != null ? new ResponseEntity<>(attendance, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Save attendance
    @PostMapping("/add")
    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
        Attendance savedAttendance = attendanceService.saveAttendance(attendance);
        return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
    }

    // Update attendance
    @PutMapping("/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance updatedAttendance) {
        Attendance attendance = attendanceService.updateAttendance(id, updatedAttendance);
        return attendance != null ? new ResponseEntity<>(attendance, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete attendance by ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendanceById(id);
        return ResponseEntity.noContent().build();
    }
}
