package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PayrollDeduction;

@Repository
public interface PayrollDeductionRepository extends JpaRepository<PayrollDeduction, Long> {

    List<PayrollDeduction> findByPayrollEmployeeId(Long payrollEmployeeId);

    void deleteByPayrollEmployeeId(Long payrollEmployeeId);

    List<PayrollDeduction> findByPayrollEmployeeIdIn(List<Long> payrollEmployeeIds);

}
