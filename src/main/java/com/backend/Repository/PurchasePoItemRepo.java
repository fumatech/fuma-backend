package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.ProductPurchaseReportDTO;
import com.backend.Entity.PurchasePoItem;

@Repository
public interface PurchasePoItemRepo extends JpaRepository<PurchasePoItem, Long> {

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
			    FROM PurchasePoItem i
			    JOIN i.purchasePoOrder o
			    LEFT JOIN ProductVariations pv ON pv.id = i.productVariationId
			""")
	List<ProductPurchaseReportDTO> fetchAllPOItems();

}
