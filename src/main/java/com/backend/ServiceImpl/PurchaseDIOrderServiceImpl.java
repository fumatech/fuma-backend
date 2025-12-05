package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PurchaseDIItem;
import com.backend.Entity.PurchaseDIOrder;
import com.backend.Entity.ShippingDIDetails;
import com.backend.Entity.StockTransaction;
import com.backend.Repository.PurchaseDIOrderRepo;

import jakarta.transaction.Transactional;

@Service
public class PurchaseDIOrderServiceImpl implements com.backend.Service.PurchaseDIOrderService {

	@Autowired
	private PurchaseDIOrderRepo purchaseOrderRepo;

	@Transactional
	@Override
	public PurchaseDIOrder savePurchaseDIOrder(PurchaseDIOrder purchaseDIOrder) {

		if (purchaseDIOrder.getPurchaseDIItem() != null) {
			for (PurchaseDIItem item : purchaseDIOrder.getPurchaseDIItem()) {
				item.setPurchaseDIOrder(purchaseDIOrder);
			}
		}

		if (purchaseDIOrder.getShippingDIDetails() != null) {
			for (ShippingDIDetails ship : purchaseDIOrder.getShippingDIDetails()) {
				ship.setPurchaseDIOrder(purchaseDIOrder);
			}
		}

		if (purchaseDIOrder.getStockTransactions() != null) {
			for (StockTransaction stock : purchaseDIOrder.getStockTransactions()) {
				stock.setPurchaseDIOrder(purchaseDIOrder);
			}
		}

		return purchaseOrderRepo.save(purchaseDIOrder);
	}

	@Override
	public List<PurchaseDIOrder> getAllPurchaseDIOrders() {
		return purchaseOrderRepo.findAll();
	}

	@Override
	public Optional<PurchaseDIOrder> getPurchaseDIOrderById(Long id) {
		return purchaseOrderRepo.findById(id);
	}

	@Transactional
	@Override
	public PurchaseDIOrder updatePurchaseDIOrder(Long id, PurchaseDIOrder updatedPurchaseDIOrder) {
		Optional<PurchaseDIOrder> existingOrderOptional = purchaseOrderRepo.findById(id);

		if (existingOrderOptional.isPresent()) {
			PurchaseDIOrder existingOrder = existingOrderOptional.get();

			// Update basic fields
			existingOrder.setStatus(updatedPurchaseDIOrder.getStatus());
			existingOrder.setVendor(updatedPurchaseDIOrder.getVendor());
			existingOrder.setAddedBy(updatedPurchaseDIOrder.getAddedBy());
			existingOrder.setReferenceNumber(updatedPurchaseDIOrder.getReferenceNumber());
			existingOrder.setOrderDate(updatedPurchaseDIOrder.getOrderDate());
			existingOrder.setPayTermNumber(updatedPurchaseDIOrder.getPayTermNumber());
			existingOrder.setPayTermType(updatedPurchaseDIOrder.getPayTermType());
			existingOrder.setLocation(updatedPurchaseDIOrder.getLocation());
			existingOrder.setFile(updatedPurchaseDIOrder.getFile());
			existingOrder.setTotalItems(updatedPurchaseDIOrder.getTotalItems());
			existingOrder.setNetTotalAmount(updatedPurchaseDIOrder.getNetTotalAmount());
			existingOrder.setDiscountType(updatedPurchaseDIOrder.getDiscountType());
			existingOrder.setDiscountAmount(updatedPurchaseDIOrder.getDiscountAmount());
			existingOrder.setPurchaseTax(updatedPurchaseDIOrder.getPurchaseTax());
			existingOrder.setTaxAmount(updatedPurchaseDIOrder.getTaxAmount());
			existingOrder.setAdditionalNotes(updatedPurchaseDIOrder.getAdditionalNotes());

			if (updatedPurchaseDIOrder.getPurchaseDIItem() != null) {
				existingOrder.getPurchaseDIItem().clear();
				for (PurchaseDIItem item : updatedPurchaseDIOrder.getPurchaseDIItem()) {
					item.setPurchaseDIOrder(existingOrder);
					existingOrder.getPurchaseDIItem().add(item);
				}
			}

			if (updatedPurchaseDIOrder.getShippingDIDetails() != null) {
				existingOrder.getShippingDIDetails().clear();
				for (ShippingDIDetails ship : updatedPurchaseDIOrder.getShippingDIDetails()) {
					ship.setPurchaseDIOrder(existingOrder);
					existingOrder.getShippingDIDetails().add(ship);
				}
			}

			// Update StockTransactions
			if (updatedPurchaseDIOrder.getStockTransactions() != null) {
				existingOrder.getStockTransactions().clear();
				for (StockTransaction stock : updatedPurchaseDIOrder.getStockTransactions()) {
					stock.setPurchaseDIOrder(existingOrder);
					existingOrder.getStockTransactions().add(stock);
				}
			}

			return purchaseOrderRepo.save(existingOrder);
		}
		return null;
	}

	@Override
	public void deletePurchaseDIOrder(Long id) {
		if (purchaseOrderRepo.existsById(id)) {
			purchaseOrderRepo.deleteById(id);
		}
	}

	@Override
	public List<String> getAllOrderIds() {
		return null;
	}
}
