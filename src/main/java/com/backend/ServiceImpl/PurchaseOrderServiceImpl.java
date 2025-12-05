package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.OrderItems;
import com.backend.Entity.PurchaseOrder;
import com.backend.Repository.PurchaseOrderRepo;
import com.backend.Service.IdGenerator;
import com.backend.Service.PurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepo purchaseOrderRepo;
	@Autowired
	private IdGenerator idGenerator;

	@Override
	public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
		if (purchaseOrder.getOrderItems() != null) {
			purchaseOrder.setIdGenerator(idGenerator);

			for (OrderItems item : purchaseOrder.getOrderItems()) {
				item.setPurchaseOrder(purchaseOrder);
			}
		}
		return purchaseOrderRepo.save(purchaseOrder);
	}

	@Override
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return purchaseOrderRepo.findAll();
	}

	@Override
	public Optional<PurchaseOrder> getPurchaseOrderById(Long id) {
		return purchaseOrderRepo.findById(id);
	}

	@Override
	public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder updatedPurchaseOrder) {
		Optional<PurchaseOrder> existingOrder = purchaseOrderRepo.findById(id);

		if (existingOrder.isPresent()) {
			PurchaseOrder purchaseOrder = existingOrder.get();
			purchaseOrder.setVendor(updatedPurchaseOrder.getVendor());
			purchaseOrder.setStatus(updatedPurchaseOrder.getStatus());
			purchaseOrder.setAddedBy(updatedPurchaseOrder.getAddedBy());
			purchaseOrder.setReferenceNumber(updatedPurchaseOrder.getReferenceNumber());
			purchaseOrder.setOrderDate(updatedPurchaseOrder.getOrderDate());
			purchaseOrder.setDeliveryDate(updatedPurchaseOrder.getDeliveryDate());
			purchaseOrder.setLocation(updatedPurchaseOrder.getLocation());
			purchaseOrder.setFile(updatedPurchaseOrder.getFile());
			purchaseOrder.setTotalItems(updatedPurchaseOrder.getTotalItems());
			purchaseOrder.setTotalShippedItems(updatedPurchaseOrder.getTotalShippedItems());
			purchaseOrder.setAdditionalNotes(updatedPurchaseOrder.getAdditionalNotes());

			purchaseOrder.getOrderItems().clear();
			purchaseOrder.getOrderItems().addAll(updatedPurchaseOrder.getOrderItems());

			for (OrderItems item : purchaseOrder.getOrderItems()) {
				item.setPurchaseOrder(purchaseOrder);
			}

			return purchaseOrderRepo.save(purchaseOrder);
		}

		return null;
	}

	@Override
	public void deletePurchaseOrder(Long id) {
		if (purchaseOrderRepo.existsById(id)) {
			purchaseOrderRepo.deleteById(id);
		}
	}

	@Override
	public List<String> getAllOrderIds() {
		return purchaseOrderRepo.findAll().stream().map(PurchaseOrder::getPurchaseOrderId).collect(Collectors.toList());
	}

	@Override
	public Optional<PurchaseOrder> getPurchaseOrderByPoId(String purchaseOrderId) {
		return Optional.ofNullable(purchaseOrderRepo.findByPurchaseOrderId(purchaseOrderId));
	}

	@Override
	public List<String> getPurchaseOrderIdsByStatus(Long status) {
		return purchaseOrderRepo.findPurchaseOrderIdsByStatus(status);
	}

	@Override
	public List<PurchaseOrder> getPendingOrders() {
		return purchaseOrderRepo.findByPurchaseStatus(0L);
	}

	@Override
	public List<PurchaseOrder> getAcceptedOrders() {
		return purchaseOrderRepo.findByPurchaseStatus(1L);
	}

	@Override
	public List<PurchaseOrder> getRejectedOrders() {
		return purchaseOrderRepo.findByPurchaseStatus(2L);
	}

	@Override
	public List<PurchaseOrder> getShipOrders() {
		return purchaseOrderRepo.findByPurchaseStatus(3L);
	}

	@Override
	public List<PurchaseOrder> getFinalOrders() {
		return purchaseOrderRepo.findByPurchaseStatus(4L);

	}

	@Override
	public Long getTotalShippedItems(String purchaseOrderId) {
		return purchaseOrderRepo.getTotalShippedItems(purchaseOrderId);
	}

}
