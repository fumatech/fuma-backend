package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.SaleReturn;


public interface SaleReturnService {
	
SaleReturn saveSaleReturn(SaleReturn saleReturn);
	
	List<SaleReturn> getAllSaleReturns();
	
	
    Optional<SaleReturn> getSaleReturnById(Long id);

    SaleReturn updateSaleReturn(Long id, SaleReturn saleReturn); // New method for update
    
    void deleteSaleReturn(Long id); // New method for delete
    
    public List<String> getAllReturnIds();
    
    Optional<SaleReturn> getSaleReturnByPRId(String id);
    
    List<String> getSaleReturnIdsByStatus(Long status);
    
    
    

    List<SaleReturn> getPendingOrders();  // New method for pending orders
    List<SaleReturn> getAcceptedOrders(); // New method for accepted orders
    List<SaleReturn> getRejectedOrders();
    List<SaleReturn> getShipOrders();


    Long getTotalShippedItems(String purchaseReturnId);

    

}
