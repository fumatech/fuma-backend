package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.SaleDIItem;
import com.backend.Entity.SaleDIOrder;
import com.backend.Entity.ShippingSaleDIDetails;
import com.backend.Entity.StockTransaction;
import com.backend.Repository.SaleDIOrderRepo;
import com.backend.Service.SaleDIOrderService;

@Service
public class SaleDIOrderServiceImpl implements SaleDIOrderService {

	@Autowired
	private SaleDIOrderRepo saleDIOrderRepo;

	@Autowired
	private com.backend.Service.IdGenerator idGenerator;

	@Autowired
	private com.backend.Service.StockTransactionService stockTransactionService;

	@Override
	@Transactional
	public SaleDIOrder saveSaleDIOrder(SaleDIOrder saleDIOrder) {

		saleDIOrder.setIdGenerator(idGenerator);

		if (saleDIOrder.getSaleDIItem() != null) {
			for (SaleDIItem item : saleDIOrder.getSaleDIItem()) {
				item.setSaleDIOrder(saleDIOrder);
			}
		}

		if (saleDIOrder.getShippingSaleDIDetails() != null) {
			for (ShippingSaleDIDetails ship : saleDIOrder.getShippingSaleDIDetails()) {
				ship.setSaleDIOrder(saleDIOrder);
			}
		}

		if (saleDIOrder.getStockTransactions() != null) {
			for (StockTransaction stock : saleDIOrder.getStockTransactions()) {
				stock.setSaleDIOrder(saleDIOrder);
			}
			// Keep stock validation consistent with SO sale flow.
			stockTransactionService.validateStockTransactions(saleDIOrder.getStockTransactions());
		}
		return saleDIOrderRepo.save(saleDIOrder);
	}

	@Override
	public List<SaleDIOrder> getAllSaleDIOrders() {
		// TODO Auto-generated method stub
		return saleDIOrderRepo.findAll();
	}

	@Override
	public Optional<SaleDIOrder> getSaleDIOrderById(Long id) {
		// TODO Auto-generated method stub
		return saleDIOrderRepo.findById(id);
	}

	@Override
	public void deleteSaleDIOrder(Long id) {
		// TODO Auto-generated method stub
		if (saleDIOrderRepo.existsById(id)) {
			saleDIOrderRepo.deleteById(id);
		}
	}

	@Override
	public List<String> getAllOrderIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public SaleDIOrder updateSaleDIOrder(Long id, SaleDIOrder saleDIOrder) {
		Optional<SaleDIOrder> existingOrderOpt = saleDIOrderRepo.findById(id);

		if (existingOrderOpt.isPresent()) {
			SaleDIOrder existingOrder = existingOrderOpt.get();

			// Update basic fields
			existingOrder.setSaleDIOrderId(saleDIOrder.getSaleDIOrderId());
			existingOrder.setFranchise(saleDIOrder.getFranchise());
			existingOrder.setReferenceNumber(saleDIOrder.getReferenceNumber());
			existingOrder.setAddedBy(saleDIOrder.getAddedBy());
			existingOrder.setSaleDate(saleDIOrder.getSaleDate());
			existingOrder.setCustomerId(saleDIOrder.getCustomerId());
			existingOrder.setPayTermNumber(saleDIOrder.getPayTermNumber());
			existingOrder.setPayTermType(saleDIOrder.getPayTermType());
			existingOrder.setLocation(saleDIOrder.getLocation());
			existingOrder.setTotalItems(saleDIOrder.getTotalItems());
			existingOrder.setNetTotalAmount(saleDIOrder.getNetTotalAmount());
			existingOrder.setDiscountType(saleDIOrder.getDiscountType());
			existingOrder.setDiscountAmount(saleDIOrder.getDiscountAmount());
			existingOrder.setPurchaseTax(saleDIOrder.getPurchaseTax());
			existingOrder.setTaxAmount(saleDIOrder.getTaxAmount());
			existingOrder.setAdditionalNotes(saleDIOrder.getAdditionalNotes());

			// Update child relationships: clear and add again (to handle orphanRemoval)
			existingOrder.getSaleDIItem().clear();
			if (saleDIOrder.getSaleDIItem() != null) {
				for (SaleDIItem item : saleDIOrder.getSaleDIItem()) {
					item.setSaleDIOrder(existingOrder); // set parent
				}
				existingOrder.getSaleDIItem().addAll(saleDIOrder.getSaleDIItem());
			}

			existingOrder.getShippingSaleDIDetails().clear();
			if (saleDIOrder.getShippingSaleDIDetails() != null) {
				for (ShippingSaleDIDetails shipping : saleDIOrder.getShippingSaleDIDetails()) {
					shipping.setSaleDIOrder(existingOrder); // Add this setter if missing in entity
				}
				existingOrder.getShippingSaleDIDetails().addAll(saleDIOrder.getShippingSaleDIDetails());
			}

			existingOrder.getStockTransactions().clear();
			if (saleDIOrder.getStockTransactions() != null) {
				for (StockTransaction stock : saleDIOrder.getStockTransactions()) {
					stock.setSaleDIOrder(existingOrder); // Add this setter if missing in entity
				}
				stockTransactionService.validateStockTransactions(saleDIOrder.getStockTransactions());
				existingOrder.getStockTransactions().addAll(saleDIOrder.getStockTransactions());
			}

			// Save and return
			return saleDIOrderRepo.save(existingOrder);
		} else {
			return null;
		}
	}

	@Override
	public String getNextReferenceNumber() {
		List<SaleDIOrder> results = saleDIOrderRepo.findTopOrderByReferenceNumber(PageRequest.of(0, 1));
		if (!results.isEmpty()) {
			String lastRef = results.get(0).getReferenceNumber(); // e.g., "FUMADI12"
			try {
				String numberPart = lastRef.replaceAll("\\D+", ""); // extract digits, e.g., "12"
				int nextNumber = Integer.parseInt(numberPart) + 1;
				return "FUMADIS" + nextNumber;
			} catch (NumberFormatException e) {
				return "FUMADIS1";
			}
		}
		return "FUMADIS1";
	}
}
