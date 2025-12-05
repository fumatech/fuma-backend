package com.backend.Service;

import java.util.List;

import com.backend.Entity.PayComponent;

public interface PayComponentService {
	
	PayComponent savePayComponent(PayComponent payComponent);
	
	List<PayComponent> getAllPayComponents();

	PayComponent updatePayComponent(Long id, PayComponent updatedPayComponent);

	PayComponent getPayComponentById(Long id);

	void deletePayComponentById(Long id);

}
