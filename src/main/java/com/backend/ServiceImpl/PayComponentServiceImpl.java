package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PayComponent;
import com.backend.Repository.PayComponentRepo;
import com.backend.Service.PayComponentService;

@Service
public class PayComponentServiceImpl implements PayComponentService {

	@Autowired
	private PayComponentRepo payComponentRepo;

	@Override
	public PayComponent savePayComponent(PayComponent payComponent) {
		return payComponentRepo.save(payComponent);
	}

	@Override
	public List<PayComponent> getAllPayComponents() {
		return payComponentRepo.findAll();
	}

	@Override
	public PayComponent getPayComponentById(Long id) {
		return payComponentRepo.findById(id).orElse(null);
	}

	@Override
	public PayComponent updatePayComponent(Long id, PayComponent updated) {
		return payComponentRepo.findById(id).map(pc -> {
			pc.setDescription(updated.getDescription());
			pc.setType(updated.getType());
			pc.setAmountType(updated.getAmountType());
			pc.setAmount(updated.getAmount());
			pc.setApplicableDate(updated.getApplicableDate());
			pc.setEmployeeId(updated.getEmployeeId());
			return payComponentRepo.save(pc);
		}).orElse(null);
	}

	@Override
	public void deletePayComponentById(Long id) {
		payComponentRepo.deleteById(id);
	}
}
