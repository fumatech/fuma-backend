package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.Entity.SaleSoItem;
import com.backend.Entity.SellReportDTO;

public interface SaleSoItemRepo extends JpaRepository<SaleSoItem, Long> {

	@Query("""
			    SELECT new com.backend.Entity.SellReportDTO(
			        i.productName,
			        i.productSku,
			        o.customerId,
			        o.franchise,
			        o.referenceNumber,
			        o.saleDate,
			        i.quantity,
			        i.unitCostBeforeDiscount,
			        i.discountPercent,
			        i.taxAmount,
			        unitSellingPrice,
			        i.lineTotal
			    )
			    FROM SaleSoItem i
			    JOIN i.saleSoOrder o
			""")
	List<SellReportDTO> getSoSellReport();
}
