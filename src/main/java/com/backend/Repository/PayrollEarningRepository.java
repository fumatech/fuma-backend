package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PayrollEarning;

@Repository
public interface PayrollEarningRepository extends JpaRepository<PayrollEarning, Long> {
	List<PayrollEarning> findByPayrollEmployeeId(Long payrollEmployeeId);

	void deleteByPayrollEmployeeId(Long payrollEmployeeId);

}
