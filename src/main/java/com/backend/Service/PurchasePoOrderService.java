package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.PurchasePoOrder;

public interface PurchasePoOrderService {
    PurchasePoOrder savePurchasePoOrder(PurchasePoOrder PurchasePoOrder);
	
	List<PurchasePoOrder> getAllPurchasePoOrders();
	
	
    Optional<PurchasePoOrder> getPurchasePoOrderById(Long id);

    PurchasePoOrder updatePurchasePoOrder(Long id, PurchasePoOrder PurchasePoOrder); // New method for update
    
    void deletePurchasePoOrder(Long id); // New method for delete
    
    public List<String> getAllOrderIds();


}
