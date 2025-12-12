package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.Entity.ProductPurchaseReportDTO;
import com.backend.Entity.StockAdjustmentItems;

public interface StockAdjustmentItemsRepo extends JpaRepository<StockAdjustmentItems, Long> {

	@Query("""
			    SELECT new com.backend.Entity.ProductPurchaseReportDTO(
			        sai.productName,
			        sai.productSku,
			        sa.businessLocation,
			        sa.referenceNumber,
			        sa.date,
			        sai.quantity,
			        sai.unitSellingPrice,
			        sai.lineTotal,
			        sa.totalUnits,
			        null,
			        null
			    )
			    FROM StockAdjustmentItems sai
			    JOIN sai.stockAdjustment sa
			""")
	List<ProductPurchaseReportDTO> fetchAllStockAdjustmentAsPurchaseReport();

}
