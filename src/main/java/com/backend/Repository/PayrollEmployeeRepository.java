package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PayrollEmployee;

@Repository
public interface PayrollEmployeeRepository extends JpaRepository<PayrollEmployee, Long> {

    List<PayrollEmployee> findByPayrollId(Long payrollId);

    void deleteByPayrollId(Long payrollId);

    List<PayrollEmployee> findByPayrollIdIn(List<Long> payrollIds);

    @Query("SELECT COUNT(pe) FROM PayrollEmployee pe")
    long countAll();

    @Query("SELECT COALESCE(SUM(pe.total), 0) FROM PayrollEmployee pe")
    double sumTotal();

    @Query("SELECT p.month, COALESCE(SUM(pe.total), 0) FROM PayrollEmployee pe JOIN PayRoll p ON pe.payrollId = p.id GROUP BY p.month ORDER BY p.month")
    List<Object[]> sumTotalGroupByMonth();

}
