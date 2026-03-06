package com.backend.Service;

import java.util.List;

import com.backend.Entity.EmployeeGrievance;

public interface EmployeeGrievanceService {

    EmployeeGrievance save(EmployeeGrievance grievance);

    List<EmployeeGrievance> getAll();

    EmployeeGrievance getById(Long id);

    List<EmployeeGrievance> getByEmployeeId(Long employeeId);

    List<EmployeeGrievance> getByStatus(String status);

    List<EmployeeGrievance> getByCategory(String category);

    List<EmployeeGrievance> getByPriority(String priority);

    List<EmployeeGrievance> getByAssignedTo(Long assignedTo);

    List<EmployeeGrievance> getByFeedbackType(String feedbackType);

    EmployeeGrievance update(Long id, EmployeeGrievance updatedGrievance);

    boolean deleteById(Long id);
}
