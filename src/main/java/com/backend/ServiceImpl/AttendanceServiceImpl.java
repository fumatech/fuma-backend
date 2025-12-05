package com.backend.ServiceImpl;

import com.backend.Entity.Attendance;
import com.backend.Repository.AttendanceRepo;
import com.backend.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    // Save Attendance
    @Override
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepo.save(attendance);
    }

    // Get All Attendances
    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepo.findAll();
    }

    // Update Attendance
    @Override
    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        Optional<Attendance> existingAttendanceOpt = attendanceRepo.findById(id);
        if (existingAttendanceOpt.isPresent()) {
            Attendance existingAttendance = existingAttendanceOpt.get();
            // Assuming you want to update all fields.
            existingAttendance.setEmployee(updatedAttendance.getEmployee());
            existingAttendance.setInTime(updatedAttendance.getInTime());
            existingAttendance.setOutTime(updatedAttendance.getOutTime());
            existingAttendance.setShift(updatedAttendance.getShift());
            existingAttendance.setIpAddress(updatedAttendance.getIpAddress());
            existingAttendance.setInNote(updatedAttendance.getInNote());
            existingAttendance.setOutNote(updatedAttendance.getOutNote());
            return attendanceRepo.save(existingAttendance);
        } else {
            return null;  // If attendance with the given ID doesn't exist
        }
    }

    // Get Attendance by ID
    @Override
    public Attendance getAttendanceById(Long id) {
        return attendanceRepo.findById(id).orElse(null);
    }

    // Delete Attendance by ID
    @Override
    public void deleteAttendanceById(Long id) {
        attendanceRepo.deleteById(id);
    }
}
