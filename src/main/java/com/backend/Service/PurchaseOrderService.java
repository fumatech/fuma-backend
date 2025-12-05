package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.PurchaseOrder;

public interface PurchaseOrderService {

	PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);

	List<PurchaseOrder> getAllPurchaseOrders();

	Optional<PurchaseOrder> getPurchaseOrderById(Long id);

	PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrder);

	void deletePurchaseOrder(Long id);

	public List<String> getAllOrderIds();

	Optional<PurchaseOrder> getPurchaseOrderByPoId(String id);

	List<String> getPurchaseOrderIdsByStatus(Long status);

	List<PurchaseOrder> getPendingOrders();

	List<PurchaseOrder> getAcceptedOrders();

	List<PurchaseOrder> getRejectedOrders();

	List<PurchaseOrder> getShipOrders();

	List<PurchaseOrder> getFinalOrders();

	Long getTotalShippedItems(String purchaseOrderId);

}
