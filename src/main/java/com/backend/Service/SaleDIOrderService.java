package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.SaleDIOrder;

public interface SaleDIOrderService {

	SaleDIOrder saveSaleDIOrder(SaleDIOrder saleDIOrder);

	List<SaleDIOrder> getAllSaleDIOrders();

	Optional<SaleDIOrder> getSaleDIOrderById(Long id);

	SaleDIOrder updateSaleDIOrder(Long id, SaleDIOrder saleDIOrder);

	void deleteSaleDIOrder(Long id);

	public List<String> getAllOrderIds();

	String getNextReferenceNumber();

}
