package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.FranchisePurchaseOrder;

public interface FranchisePurchaseOrderService {

	FranchisePurchaseOrder savePurchaseOrder(FranchisePurchaseOrder franchisePurchaseOrder);

	List<FranchisePurchaseOrder> getAllPurchaseOrders();

	Optional<FranchisePurchaseOrder> getPurchaseOrderById(Long id);

	FranchisePurchaseOrder updatePurchaseOrder(Long id, FranchisePurchaseOrder franchisePurchaseOrder); // New method
																										// for update

	void deletePurchaseOrder(Long id); // New method for delete

	public List<String> getAllOrderIds();

	Optional<FranchisePurchaseOrder> getPurchaseOrderByPoId(String id);

	List<String> getPurchaseOrderIdsByStatus(Long status);

	List<FranchisePurchaseOrder> getPendingOrders();

	List<FranchisePurchaseOrder> getAcceptedOrders();

	List<FranchisePurchaseOrder> getRejectedOrders();

	List<FranchisePurchaseOrder> getShipOrders();

	Long getTotalShippedItems(String franchisePurchaseOrderId);

	FranchisePurchaseOrder updateDispatchStatus(Long id, String dispatchStatus);

}
