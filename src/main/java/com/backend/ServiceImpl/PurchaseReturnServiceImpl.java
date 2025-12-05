package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.PurchaseReturn;
import com.backend.Entity.PurchaseReturnItems;
import com.backend.Entity.StockTransaction;
import com.backend.Repository.PurchaseReturnRepo;
import com.backend.Service.IdGenerator;
import com.backend.Service.PurchaseReturnService;

@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

	@Autowired
	private PurchaseReturnRepo purchaseReturnRepo;
	@Autowired
	private IdGenerator idGenerator;

	@Override
	public PurchaseReturn savePurchaseReturn(PurchaseReturn purchaseReturn) {
		if (purchaseReturn.getPurchaseReturnItems() != null) {
			purchaseReturn.setIdGenerator(idGenerator); // Pass the IdGenerator to the entity

			for (PurchaseReturnItems item : purchaseReturn.getPurchaseReturnItems()) {
				item.setPurchaseReturn(purchaseReturn);
			}
		}

		if (purchaseReturn.getStockTransactions() != null) {
			for (StockTransaction stock : purchaseReturn.getStockTransactions()) {
				stock.setPurchaseReturn(purchaseReturn);
			}
		}
		return purchaseReturnRepo.save(purchaseReturn);
	}

	@Override
	public List<PurchaseReturn> getAllPurchaseReturns() {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findAll();
	}

	@Override
	public Optional<PurchaseReturn> getPurchaseReturnById(Long id) {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findById(id);
	}

	@Override
	public PurchaseReturn updatePurchaseReturn(Long id, PurchaseReturn updatedPurchaseReturn) {
		Optional<PurchaseReturn> existingOrder = purchaseReturnRepo.findById(id);

		if (existingOrder.isPresent()) {
			PurchaseReturn purchaseReturn = existingOrder.get();
			purchaseReturn.setVendor(updatedPurchaseReturn.getVendor());
			purchaseReturn.setStatus(updatedPurchaseReturn.getStatus());
			purchaseReturn.setAddedBy(updatedPurchaseReturn.getAddedBy());
			purchaseReturn.setReferenceNumber(updatedPurchaseReturn.getReferenceNumber());
			purchaseReturn.setInvoiceNumber(updatedPurchaseReturn.getInvoiceNumber());
			purchaseReturn.setReceipt(updatedPurchaseReturn.getReceipt());
			purchaseReturn.setPaymentStatus(updatedPurchaseReturn.getPaymentStatus());
			purchaseReturn.setOrderDate(updatedPurchaseReturn.getOrderDate());
			purchaseReturn.setTotalAmount(updatedPurchaseReturn.getTotalAmount());
			purchaseReturn.setTotalItems(updatedPurchaseReturn.getTotalItems());
			purchaseReturn.setPurchaseTax(updatedPurchaseReturn.getPurchaseTax());
			purchaseReturn.setTotalShippedItems(updatedPurchaseReturn.getTotalShippedItems());
			purchaseReturn.setAdditionalNotes(updatedPurchaseReturn.getAdditionalNotes());

			// Update the order items
			purchaseReturn.getPurchaseReturnItems().clear();
			purchaseReturn.getPurchaseReturnItems().addAll(updatedPurchaseReturn.getPurchaseReturnItems());

			// Set purchase order reference in each item
			for (PurchaseReturnItems item : purchaseReturn.getPurchaseReturnItems()) {
				item.setPurchaseReturn(purchaseReturn);
			}

			return purchaseReturnRepo.save(purchaseReturn);
		}

		return null; // or throw an exception if not found
	}

	@Override
	public void deletePurchaseReturn(Long id) {
		if (purchaseReturnRepo.existsById(id)) {
			purchaseReturnRepo.deleteById(id);
		}
	}

	@Override
	public List<String> getAllReturnIds() {
		return purchaseReturnRepo.findAll().stream().map(PurchaseReturn::getPurchaseReturnId)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<PurchaseReturn> getPurchaseReturnByPRId(String purchaseReturnId) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(purchaseReturnRepo.findByPurchaseReturnId(purchaseReturnId));
	}

	@Override
	public List<String> getPurchaseReturnIdsByStatus(Long status) {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findPurchaseReturnIdsByStatus(status);
	}

	@Override
	public List<PurchaseReturn> getPendingOrders() {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findByPurchaseReturnStatus(0L); // Fetch orders with status 0
	}

	@Override
	public List<PurchaseReturn> getAcceptedOrders() {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findByPurchaseReturnStatus(1L); // Fetch orders with status 0
	}

	@Override
	public List<PurchaseReturn> getRejectedOrders() {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findByPurchaseReturnStatus(2L); // Fetch orders with status 0
	}

	@Override
	public List<PurchaseReturn> getShipOrders() {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findByPurchaseReturnStatus(3L); // Fetch orders with status 0
	}

	@Override
	public Long getTotalShippedItems(String purchaseReturnId) {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.getTotalShippedItems(purchaseReturnId);
	}

	@Override
	public List<PurchaseReturn> getReturnedOrders() {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findByPurchaseReturnStatus(4L); // Fetch orders with status 0
	}

}
