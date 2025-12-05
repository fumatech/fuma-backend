package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.StockAdjustment;

public interface StockAdjustmentService {
	
	
	 StockAdjustment saveStockAdjustment(StockAdjustment stockAdjustment);
		
		List<StockAdjustment> getAllStockAdjustments();
		
		
	    Optional<StockAdjustment> getStockAdjustmentById(Long id);

	    StockAdjustment updateStockAdjustment(Long id, StockAdjustment stockAdjustment); 
	    
	    void deleteStockAdjustment(Long id); 
	    

}
