package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.FranchiseOrderItems;
import com.backend.Entity.FranchisePurchaseOrder;
import com.backend.Repository.FranchisePurchaseOrderRepo;
import com.backend.Service.FranchisePurchaseOrderService;
import com.backend.Service.IdGenerator;

@Service
public class FranchisePurchaseOrderServiceImpl implements FranchisePurchaseOrderService {

	@Autowired
	private FranchisePurchaseOrderRepo franchisePurchaseOrderRepo;

	@Autowired
	private IdGenerator idGenerator;

	@Override
	public FranchisePurchaseOrder savePurchaseOrder(FranchisePurchaseOrder franchisePurchaseOrder) {
		if (franchisePurchaseOrder.getFranchiseOrderItems() != null) {
			franchisePurchaseOrder.setIdGenerator(idGenerator);

			for (FranchiseOrderItems item : franchisePurchaseOrder.getFranchiseOrderItems()) {
				item.setFranchisePurchaseOrder(franchisePurchaseOrder);
			}
		}
		return franchisePurchaseOrderRepo.save(franchisePurchaseOrder);
	}

	@Override
	public List<FranchisePurchaseOrder> getAllPurchaseOrders() {
		return franchisePurchaseOrderRepo.findAll();
	}

	@Override
	public Optional<FranchisePurchaseOrder> getPurchaseOrderById(Long id) {
		return franchisePurchaseOrderRepo.findById(id);
	}

	@Override
	public FranchisePurchaseOrder updatePurchaseOrder(Long id, FranchisePurchaseOrder updatedOrder) {
		Optional<FranchisePurchaseOrder> existingOrder = franchisePurchaseOrderRepo.findById(id);

		if (existingOrder.isPresent()) {
			FranchisePurchaseOrder order = existingOrder.get();

			// Server-side validation: Ensure shipping quantity does not exceed ordered
			// quantity
			if (updatedOrder.getFranchiseOrderItems() != null) {
				for (FranchiseOrderItems updatedItem : updatedOrder.getFranchiseOrderItems()) {
					if (updatedItem.getUpdatedQuantity() != null && updatedItem.getQuantity() != null) {
						if (updatedItem.getUpdatedQuantity() > updatedItem.getQuantity()) {
							throw new RuntimeException("Shipping quantity (" + updatedItem.getUpdatedQuantity()
									+ ") cannot exceed ordered quantity (" + updatedItem.getQuantity()
									+ ") for product: " + updatedItem.getProductName());
						}
					}
					if (updatedItem.getUpdatedQuantity() != null && updatedItem.getUpdatedQuantity() < 0) {
						throw new RuntimeException(
								"Shipping quantity cannot be negative for product: " + updatedItem.getProductName());
					}
				}
			}

			order.setVendor(updatedOrder.getVendor());
			order.setStatus(updatedOrder.getStatus());
			order.setAddedBy(updatedOrder.getAddedBy());
			order.setOrderedBy(updatedOrder.getOrderedBy());
			order.setReferenceNumber(updatedOrder.getReferenceNumber());
			order.setOrderDate(updatedOrder.getOrderDate());
			order.setLocation(updatedOrder.getLocation());
			order.setFile(updatedOrder.getFile());
			order.setTotalItems(updatedOrder.getTotalItems());
			order.setTotalShippedItems(updatedOrder.getTotalShippedItems());
			order.setAdditionalNotes(updatedOrder.getAdditionalNotes());
			order.setCustomerId(updatedOrder.getCustomerId());
			order.setFranchiseName(updatedOrder.getFranchiseName());
			order.setDeliveryDate(updatedOrder.getDeliveryDate());
			order.setDispatchStatus(updatedOrder.getDispatchStatus());
			order.getFranchiseOrderItems().clear();
			order.getFranchiseOrderItems().addAll(updatedOrder.getFranchiseOrderItems());

			for (FranchiseOrderItems item : order.getFranchiseOrderItems()) {
				item.setFranchisePurchaseOrder(order);
			}

			return franchisePurchaseOrderRepo.save(order);
		}

		return null;
	}

	@Override
	public void deletePurchaseOrder(Long id) {
		if (franchisePurchaseOrderRepo.existsById(id)) {
			franchisePurchaseOrderRepo.deleteById(id);
		}
	}

	@Override
	public List<String> getAllOrderIds() {
		return franchisePurchaseOrderRepo.findAll().stream().map(FranchisePurchaseOrder::getFranchisePurchaseOrderId)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<FranchisePurchaseOrder> getPurchaseOrderByPoId(String id) {
		return franchisePurchaseOrderRepo.findAll().stream()
				.filter(order -> id.equals(order.getFranchisePurchaseOrderId())).findFirst();
	}

	@Override
	public List<String> getPurchaseOrderIdsByStatus(Long status) {
		return franchisePurchaseOrderRepo.findAll().stream().filter(order -> status.equals(order.getStatus()))
				.map(FranchisePurchaseOrder::getFranchisePurchaseOrderId).collect(Collectors.toList());
	}

	@Override
	public List<FranchisePurchaseOrder> getPendingOrders() {
		return franchisePurchaseOrderRepo.findAll().stream()
				.filter(order -> order.getStatus() != null && order.getStatus() == 0L).collect(Collectors.toList());
	}

	@Override
	public List<FranchisePurchaseOrder> getAcceptedOrders() {
		return franchisePurchaseOrderRepo.findAll().stream()
				.filter(order -> order.getStatus() != null && order.getStatus() == 1L).collect(Collectors.toList());
	}

	@Override
	public List<FranchisePurchaseOrder> getRejectedOrders() {
		return franchisePurchaseOrderRepo.findAll().stream()
				.filter(order -> order.getStatus() != null && order.getStatus() == 2L).collect(Collectors.toList());
	}

	@Override
	public List<FranchisePurchaseOrder> getShipOrders() {
		return franchisePurchaseOrderRepo.findAll().stream()
				.filter(order -> order.getStatus() != null && order.getStatus() == 3L).collect(Collectors.toList());
	}

	@Override
	public Long getTotalShippedItems(String franchisePurchaseOrderId) {
		return franchisePurchaseOrderRepo.findAll().stream()
				.filter(order -> franchisePurchaseOrderId.equals(order.getFranchisePurchaseOrderId()))
				.map(FranchisePurchaseOrder::getTotalShippedItems).findFirst().orElse(0L);
	}

	@Override
	public FranchisePurchaseOrder updateDispatchStatus(Long id, String dispatchStatus) {
		Optional<FranchisePurchaseOrder> existingOrder = franchisePurchaseOrderRepo.findById(id);

		if (existingOrder.isPresent()) {
			FranchisePurchaseOrder order = existingOrder.get();
			order.setDispatchStatus(dispatchStatus);
			return franchisePurchaseOrderRepo.save(order);
		}

		return null;
	}

}
