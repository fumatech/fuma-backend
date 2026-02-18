package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Entity.SaleSoItem;
import com.backend.Entity.SaleSoOrder;
import com.backend.Entity.ShippingSoDetails;
import com.backend.Entity.StockTransaction;
import com.backend.Repository.SaleSoOrderRepo;
import com.backend.Service.FranchisePurchaseOrderService;
import com.backend.Service.SaleSoOrderService;
import com.backend.Service.StockTransactionService;

@Service
public class SaleSoOrderServiceImpl implements SaleSoOrderService {

	@Autowired
	private SaleSoOrderRepo saleSoOrderRepo;

	@Autowired
	private StockTransactionService stockTransactionService;

	@Autowired
	private FranchisePurchaseOrderService franchisePurchaseOrderService;

	@Override
	@Transactional
	public SaleSoOrder saveSaleSooOrder(SaleSoOrder saleSoOrder) {
		if (saleSoOrder.getSaleSoItem() != null) {
			for (SaleSoItem item : saleSoOrder.getSaleSoItem()) {
				item.setSaleSoOrder(saleSoOrder);
			}
		}

		if (saleSoOrder.getShippingSoDetails() != null) {
			for (ShippingSoDetails ship : saleSoOrder.getShippingSoDetails()) {
				ship.setSaleSoOrder(saleSoOrder);
			}
		}

		if (saleSoOrder.getStockTransactions() != null) {
			for (StockTransaction stock : saleSoOrder.getStockTransactions()) {
				stock.setSaleSoOrder(saleSoOrder);
			}
			// Validate stock before saving
			stockTransactionService.validateStockTransactions(saleSoOrder.getStockTransactions());
		}

		// Prevent duplicate sales for same orderId
		if (saleSoOrder.getOrderId() != null && !saleSoOrder.getOrderId().isEmpty()) {
			if (saleSoOrderRepo.existsByOrderId(saleSoOrder.getOrderId())) {
				throw new RuntimeException("Sale already exists for Order ID: " + saleSoOrder.getOrderId());
			}
		}

		SaleSoOrder savedSale = saleSoOrderRepo.save(saleSoOrder);

		// Update FranchisePurchaseOrder status to 4 (Converted to Sale)
		if (savedSale.getOrderId() != null && !savedSale.getOrderId().isEmpty()) {
			franchisePurchaseOrderService.getPurchaseOrderByPoId(savedSale.getOrderId()).ifPresent(order -> {
				order.setStatus(4L);
				franchisePurchaseOrderService.savePurchaseOrder(order);
			});
		}

		return savedSale;
	}

	@Override
	public List<SaleSoOrder> getAllSaleSoOrders() {
		return saleSoOrderRepo.findAll();
	}

	@Override
	public Optional<SaleSoOrder> getSaleSoOrderById(Long id) {
		return saleSoOrderRepo.findById(id);
	}

	@Override
	@Transactional
	public SaleSoOrder updateSaleSoOrder(Long id, SaleSoOrder updatedSaleSoOrder) {
		Optional<SaleSoOrder> existingOrderOptional = saleSoOrderRepo.findById(id);

		if (existingOrderOptional.isPresent()) {
			SaleSoOrder existingOrder = existingOrderOptional.get();

			// Update basic fields
			existingOrder.setOrderId(updatedSaleSoOrder.getOrderId());
			existingOrder.setOrderRefernceNumber(updatedSaleSoOrder.getOrderRefernceNumber());
			existingOrder.setReferenceNumber(updatedSaleSoOrder.getReferenceNumber());
			existingOrder.setFranchise(updatedSaleSoOrder.getFranchise());
			existingOrder.setFranchiseId(updatedSaleSoOrder.getFranchiseId());
			existingOrder.setCustomerId(updatedSaleSoOrder.getCustomerId());
			existingOrder.setOrderedBy(updatedSaleSoOrder.getOrderedBy());
			existingOrder.setAddedBy(updatedSaleSoOrder.getAddedBy());
			existingOrder.setOrderDate(updatedSaleSoOrder.getOrderDate());
			existingOrder.setSaleDate(updatedSaleSoOrder.getSaleDate());
			existingOrder.setPayTermNumber(updatedSaleSoOrder.getPayTermNumber());
			existingOrder.setPayTermType(updatedSaleSoOrder.getPayTermType());
			existingOrder.setLocation(updatedSaleSoOrder.getLocation());
			existingOrder.setNetTotalAmount(updatedSaleSoOrder.getNetTotalAmount());
			existingOrder.setDiscountType(updatedSaleSoOrder.getDiscountType());
			existingOrder.setDiscountAmount(updatedSaleSoOrder.getDiscountAmount());
			existingOrder.setPurchaseTax(updatedSaleSoOrder.getPurchaseTax());
			existingOrder.setTaxAmount(updatedSaleSoOrder.getTaxAmount());
			existingOrder.setTotalItems(updatedSaleSoOrder.getTotalItems());
			existingOrder.setAdditionalNotes(updatedSaleSoOrder.getAdditionalNotes());

			// Update SaleSoItem
			if (updatedSaleSoOrder.getSaleSoItem() != null) {
				existingOrder.getSaleSoItem().clear();
				for (SaleSoItem item : updatedSaleSoOrder.getSaleSoItem()) {
					item.setSaleSoOrder(existingOrder);
					existingOrder.getSaleSoItem().add(item);
				}
			}

			// Update ShippingSoDetails
			if (updatedSaleSoOrder.getShippingSoDetails() != null) {
				existingOrder.getShippingSoDetails().clear();
				for (ShippingSoDetails ship : updatedSaleSoOrder.getShippingSoDetails()) {
					ship.setSaleSoOrder(existingOrder);
					existingOrder.getShippingSoDetails().add(ship);
				}
			}

			// Update StockTransactions
			if (updatedSaleSoOrder.getStockTransactions() != null) {
				existingOrder.getStockTransactions().clear();
				for (StockTransaction stock : updatedSaleSoOrder.getStockTransactions()) {
					stock.setSaleSoOrder(existingOrder);
					existingOrder.getStockTransactions().add(stock);
				}
				// Validate stock before updating
				stockTransactionService.validateStockTransactions(existingOrder.getStockTransactions());
			}

			return saleSoOrderRepo.save(existingOrder);

		}
		return null;
	}

	@Override
	@Transactional
	public void deleteSaleSoOrder(Long id) {
		Optional<SaleSoOrder> saleOrderOptional = saleSoOrderRepo.findById(id);
		if (saleOrderOptional.isPresent()) {
			SaleSoOrder saleOrder = saleOrderOptional.get();
			String orderId = saleOrder.getOrderId();

			saleSoOrderRepo.delete(saleOrder);

			// Revert FranchisePurchaseOrder status to 3 (Shipped) so it can be re-converted
			if (orderId != null && !orderId.isEmpty()) {
				franchisePurchaseOrderService.getPurchaseOrderByPoId(orderId).ifPresent(order -> {
					order.setStatus(3L);
					franchisePurchaseOrderService.savePurchaseOrder(order);
				});
			}
		}
	}

	@Override
	public List<String> getAllOrderIds() {
		return null;
	}

	@Override
	public String getNextReferenceNumber() {
		List<SaleSoOrder> results = saleSoOrderRepo.findTopOrderByReferenceNumber(PageRequest.of(0, 1));
		if (!results.isEmpty()) {
			String lastRef = results.get(0).getReferenceNumber(); // e.g. "FUMASL12"
			try {
				String numberPart = lastRef.replaceAll("\\D+", ""); // "12"
				int nextNumber = Integer.parseInt(numberPart) + 1;
				return "FUMASL" + nextNumber;
			} catch (NumberFormatException e) {
				return "FUMASL1";
			}
		}
		return "FUMASL1";
	}

}
