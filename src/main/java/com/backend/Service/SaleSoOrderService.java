package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.SaleSoOrder;
import com.backend.DTO.SaleSoOrderListDTO;

public interface SaleSoOrderService {

	SaleSoOrder saveSaleSooOrder(SaleSoOrder saleSoOrder);

	List<SaleSoOrder> getAllSaleSoOrders();

    List<SaleSoOrderListDTO> getAllSaleSoOrderSummaries();

	Optional<SaleSoOrder> getSaleSoOrderById(Long id);

	SaleSoOrder updateSaleSoOrder(Long id, SaleSoOrder saleSoOrder); // New method for update

	void deleteSaleSoOrder(Long id); // New method for delete

	public List<String> getAllOrderIds();

	String getNextReferenceNumber();

}
