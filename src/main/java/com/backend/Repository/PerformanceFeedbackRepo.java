package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PerformanceFeedback;

@Repository
public interface PerformanceFeedbackRepo extends JpaRepository<PerformanceFeedback, Long> {

    List<PerformanceFeedback> findByEmployeeId(Long employeeId);

    List<PerformanceFeedback> findByMonthAndYear(int month, int year);

    List<PerformanceFeedback> findByYear(int year);

    List<PerformanceFeedback> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);

    List<PerformanceFeedback> findByEmployeeIdAndYear(Long employeeId, int year);
}
