package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.AddExpenses;

@Repository
public interface AddExpensesRepo extends JpaRepository<AddExpenses, Long> {

	List<AddExpenses> findByTaxIsNotNull();

}
