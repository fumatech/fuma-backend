package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.EmployeeGrievance;

@Repository
public interface EmployeeGrievanceRepo extends JpaRepository<EmployeeGrievance, Long> {

    List<EmployeeGrievance> findByEmployeeId(Long employeeId);

    List<EmployeeGrievance> findByStatus(String status);

    List<EmployeeGrievance> findByCategory(String category);

    List<EmployeeGrievance> findByPriority(String priority);

    List<EmployeeGrievance> findByAssignedTo(Long assignedTo);

    List<EmployeeGrievance> findByFeedbackType(String feedbackType);

    List<EmployeeGrievance> findByAnonymous(boolean anonymous);
}
