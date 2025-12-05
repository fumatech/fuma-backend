package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.AddExpenses;
import com.backend.Entity.Transaction;
import com.backend.Repository.AddExpensesRepo;
import com.backend.Service.AddExpensesService;

import jakarta.transaction.Transactional;

@Service
public class AddExpensesServiceImpl implements AddExpensesService {

	@Autowired
	private AddExpensesRepo addExpensesRepo;

	@Override
	@Transactional
	public AddExpenses saveAddExpenses(AddExpenses addExpenses) {

		if (addExpenses.getTransaction() != null) {
			for (Transaction tx : addExpenses.getTransaction()) {
				tx.setAddExpenses(addExpenses);
			}
		}

		return addExpensesRepo.save(addExpenses);
	}
	@Override
	@Transactional
	public AddExpenses updateAddExpenses(Long id, AddExpenses updated) {
	    return addExpensesRepo.findById(id).map(existing -> {

	        updated.setId(existing.getId());

	        // Keep transaction binding
	        if (updated.getTransaction() != null) {
	            updated.getTransaction().forEach(t -> t.setAddExpenses(updated));
	        }

	        return addExpensesRepo.save(updated);

	    }).orElseThrow(() -> new RuntimeException("Expense not found with ID: " + id));
	}


	@Override
	public Optional<AddExpenses> getAddExpensesById(Long id) {
		return addExpensesRepo.findById(id);
	}

	@Override
	public List<AddExpenses> getAllAddExpenses() {
		return addExpensesRepo.findAll();
	}

	@Override
	public void deleteAddExpenses(Long id) {
		addExpensesRepo.deleteById(id);
	}
}
