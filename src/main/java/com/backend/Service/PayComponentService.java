package com.backend.Service;

import java.util.List;

import com.backend.Entity.PayComponent;

public interface PayComponentService {

	PayComponent savePayComponent(PayComponent payComponent);

	List<PayComponent> getAllPayComponents();

	PayComponent getPayComponentById(Long id);

	PayComponent updatePayComponent(Long id, PayComponent updatedPayComponent);

	void deletePayComponentById(Long id);
}
