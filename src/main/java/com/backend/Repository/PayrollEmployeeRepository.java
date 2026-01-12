package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PayrollEmployee;

@Repository
public interface PayrollEmployeeRepository extends JpaRepository<PayrollEmployee, Long> {
	List<PayrollEmployee> findByPayrollId(Long payrollId);

	void deleteByPayrollId(Long payrollId);

}
