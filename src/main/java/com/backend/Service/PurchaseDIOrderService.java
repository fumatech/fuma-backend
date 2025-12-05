package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.PurchaseDIOrder;

public interface PurchaseDIOrderService {
    PurchaseDIOrder savePurchaseDIOrder(PurchaseDIOrder PurchaseDIOrder);
	
	List<PurchaseDIOrder> getAllPurchaseDIOrders();
	
	
    Optional<PurchaseDIOrder> getPurchaseDIOrderById(Long id);

    PurchaseDIOrder updatePurchaseDIOrder(Long id, PurchaseDIOrder PurchaseDIOrder); // New method for update
    
    void deletePurchaseDIOrder(Long id); // New method for delete
    
    public List<String> getAllOrderIds();


}
