package com.backend.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.DTO.PayComponentBulkRequest;
import com.backend.Entity.PayComponent;
import com.backend.Repository.PayComponentRepo;
import com.backend.Service.PayComponentService;

@Service
public class PayComponentServiceImpl implements PayComponentService {

	@Autowired
	private PayComponentRepo payComponentRepo;

	@Override
	public PayComponent savePayComponent(PayComponent payComponent) {
		return payComponentRepo.save(payComponent); // Saves the pay component to the database
	}

	@Override
	public List<PayComponent> getAllPayComponents() {
		return payComponentRepo.findAll(); // Fetch all pay components from the database
	}

	@Override
	public PayComponent updatePayComponent(Long id, PayComponent updatedPayComponent) {
		Optional<PayComponent> existingPayComponent = payComponentRepo.findById(id);

		if (existingPayComponent.isPresent()) {
			PayComponent payComponent = existingPayComponent.get();
			payComponent.setDescription(updatedPayComponent.getDescription());
			payComponent.setType(updatedPayComponent.getType());
			payComponent.setAmountType(updatedPayComponent.getAmountType());
			payComponent.setAmount(updatedPayComponent.getAmount());
			payComponent.setApplicableDate(updatedPayComponent.getApplicableDate());

			return payComponentRepo.save(payComponent);
		}

		return null;
	}

	@Override
	public PayComponent getPayComponentById(Long id) {
		return payComponentRepo.findById(id).orElse(null);
	}

	@Override
	public void deletePayComponentById(Long id) {
		payComponentRepo.deleteById(id); // Delete the pay component with the given ID
	}

	@Override
	public void saveBulk(PayComponentBulkRequest request) {

		List<PayComponent> list = new ArrayList<>();

		for (Long empId : request.getEmployeeIds()) {

			PayComponent pc = new PayComponent();
			pc.setEmployeeId(empId);
			pc.setDescription(request.getDescription());
			pc.setType(request.getType());
			pc.setAmountType(request.getAmountType());
			pc.setAmount(request.getAmount());
			pc.setApplicableDate(request.getApplicableDate());

			list.add(pc);
		}

		payComponentRepo.saveAll(list);
	}
}
