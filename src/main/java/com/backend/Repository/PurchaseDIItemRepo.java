package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.ProductPurchaseReportDTO;
import com.backend.Entity.PurchaseDIItem;

@Repository
public interface PurchaseDIItemRepo extends JpaRepository<PurchaseDIItem, Long> {
	@Query("""
			    SELECT new com.backend.Entity.ProductPurchaseReportDTO(
			        i.productName,
			        i.productSku,
			        o.vendor,
			        o.referenceNumber,
			        o.orderDate,
			        i.quantity,
			        i.unitCostAfterDiscount,
			        i.lineTotal,
			        null,
			        pv.variationName,
			        pv.variationValue
			    )
			    FROM PurchaseDIItem i
			    JOIN i.purchaseDIOrder o
			    LEFT JOIN ProductVariations pv ON pv.id = i.productVariationId
			""")
	List<ProductPurchaseReportDTO> fetchAllDIItems();

}
