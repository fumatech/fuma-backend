package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PerformanceKPI;

@Repository
public interface PerformanceKPIRepo extends JpaRepository<PerformanceKPI, Long> {

    List<PerformanceKPI> findByEmployeeId(Long employeeId);

    List<PerformanceKPI> findByMonthAndYear(int month, int year);

    List<PerformanceKPI> findByYear(int year);

    List<PerformanceKPI> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);

    List<PerformanceKPI> findByEmployeeIdAndYear(Long employeeId, int year);
}
