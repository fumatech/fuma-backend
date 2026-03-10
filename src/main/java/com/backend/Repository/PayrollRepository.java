package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PayRoll;

@Repository
public interface PayrollRepository extends JpaRepository<PayRoll, Long> {

    List<PayRoll> findByMonthAndYear(Integer month, Integer year);
}
