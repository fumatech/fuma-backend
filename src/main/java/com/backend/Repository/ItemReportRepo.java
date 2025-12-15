package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.ItemReportDTO;
import com.backend.Entity.Product;

@Repository
public interface ItemReportRepo extends JpaRepository<Product, Long> {

	// Purchase DI Items
	@Query("""
			    SELECT new com.backend.Entity.ItemReportDTO(
			        pdi.id,
			        p.productName,
			        p.sku,
			        p.description,
			        porder.orderDate,
			        pdi.lineTotal,
			        pdi.productVariationName,
			        porder.vendor,
			        pdi.unitCostAfterDiscount,
			        null,
			        null,
			        null,
			        porder.location,
			        null,
			        null,
			        null
			    )
			    FROM PurchaseDIItem pdi
			    JOIN pdi.purchaseDIOrder porder
			    JOIN Product p ON p.id = pdi.productId
			""")
	List<ItemReportDTO> fetchPurchaseDI();

	// Purchase PO Items
	@Query("""
			    SELECT new com.backend.Entity.ItemReportDTO(
			        ppi.id,
			        p.productName,
			        p.sku,
			        p.description,
			        porder.orderDate,
			        ppi.lineTotal,
			        ppi.productVariationName,
			        porder.vendor,
			        ppi.unitCostAfterDiscount,
			        null,
			        null,
			        null,
			        porder.location,
			        null,
			        null,
			        null
			    )
			    FROM PurchasePoItem ppi
			    JOIN ppi.purchasePoOrder porder
			    JOIN Product p ON p.id = ppi.productId
			""")
	List<ItemReportDTO> fetchPurchasePO();

	// Sale DI Items
	@Query("""
			    SELECT new com.backend.Entity.ItemReportDTO(
			        sdi.id,
			        p.productName,
			        p.sku,
			        p.description,
			        null,
			        null,
			        sdi.productVariationName,
			        null,
			        null,
			        sorder.saleDate,
			        sdi.lineTotal,
			        sorder.franchise,
			        sorder.location,
			        sdi.quantity,
			        sdi.unitSellingPrice,
			        sdi.lineTotal
			    )
			    FROM SaleDIItem sdi
			    JOIN sdi.saleDIOrder sorder
			    JOIN Product p ON p.id = sdi.productId
			""")
	List<ItemReportDTO> fetchSaleDI();

	// Sale SO Items
	@Query("""
			    SELECT new com.backend.Entity.ItemReportDTO(
			        ssi.id,
			        p.productName,
			        p.sku,
			        p.description,
			        null,
			        null,
			        ssi.productVariationName,
			        null,
			        null,
			        sorder.saleDate,
			        ssi.lineTotal,
			        sorder.franchise,
			        sorder.location,
			        ssi.quantity,
			        ssi.unitSellingPrice,
			        ssi.lineTotal
			    )
			    FROM SaleSoItem ssi
			    JOIN ssi.saleSoOrder sorder
			    JOIN Product p ON p.id = ssi.productId
			""")
	List<ItemReportDTO> fetchSaleSO();
}
