package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.GroupedDateReportDTO;
import com.backend.Entity.Product;

@Repository
public interface ReportRepo extends JpaRepository<Product, Long> {

	@Query("""
			    SELECT new com.backend.Entity.GroupedDateReportDTO(

			        p.productName,
			        p.sku,
			        so.saleDate,

			        COALESCE(SUM(soi.quantity),0) + COALESCE(SUM(dii.quantity),0),

			        COALESCE(SUM(soi.lineTotal),0) + COALESCE(SUM(dii.lineTotal),0),

			        COALESCE(SUM(
			            CASE
			                WHEN st.transactionType IN
			                    ('po_purchase','di_purchase','open_stock','sale_return','product_claimed')
			                THEN st.quantity
			                WHEN st.transactionType IN
			                    ('di_sale','so_sale','purchase_return','product_replaced','adjustment')
			                THEN -st.quantity
			                ELSE 0
			            END
			        ),0)

			    )
			    FROM Product p

			    LEFT JOIN SaleSoItem soi ON soi.productId = p.id
			    LEFT JOIN soi.saleSoOrder so

			    LEFT JOIN SaleDIItem dii ON dii.productId = p.id
			    LEFT JOIN dii.saleDIOrder di

			    LEFT JOIN StockTransaction st ON st.productId = p.id

			    GROUP BY
			        p.productName,
			        p.sku,
			        so.saleDate
			    ORDER BY so.saleDate DESC
			""")
	List<GroupedDateReportDTO> getProductSalesGroupedByDate();
}
