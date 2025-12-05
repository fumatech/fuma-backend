package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

}
