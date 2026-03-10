package com.backend.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);

    List<Attendance> findByEmployeeIdAndAttendanceDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);

    List<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);

    List<Attendance> findByEmployeeId(Long employeeId);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByAttendanceDateAndLoginMethod(LocalDate attendanceDate, String loginMethod);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.attendanceDate = :date AND a.status IN ('PRESENT', 'HALF_DAY')")
    long countPresentByDate(LocalDate date);

    @Query("SELECT a.attendanceDate, COUNT(a) FROM Attendance a WHERE a.attendanceDate BETWEEN :startDate AND :endDate AND a.status IN ('PRESENT', 'HALF_DAY') GROUP BY a.attendanceDate ORDER BY a.attendanceDate")
    List<Object[]> countPresentGroupByDateBetween(LocalDate startDate, LocalDate endDate);

}
