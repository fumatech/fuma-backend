package com.backend.Service;

import java.util.List;

import com.backend.DTO.EmployeePayrollViewDTO;
import com.backend.DTO.PayrollResponseDTO;
import com.backend.DTO.PayrollSaveRequest;

public interface PayRollService {

    PayrollResponseDTO savePayroll(PayrollSaveRequest request); // return full payroll

    PayrollResponseDTO getPayrollFullById(Long id);

    List<PayrollResponseDTO> getAllPayrollFull();

    void deletePayroll(Long id);

    List<EmployeePayrollViewDTO> getEmployeeWiseList();

    List<EmployeePayrollViewDTO> getEmployeeWiseList(int month, int year);

    PayrollResponseDTO generatePayrollFromAttendance(int month, int year, String addedBy, String location);

    PayrollResponseDTO generatePayrollFromAttendance(int month, int year, String addedBy, String location,
            String employeeTypeFilter);

    PayrollResponseDTO generatePayrollForEmployee(int month, int year, String addedBy, String location,
            Long employeeId);

}
