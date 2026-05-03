package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.BrandWiseReportDTO;
import com.backend.Entity.CategoryWiseReportDTO;
import com.backend.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%')) "
            + "OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%')) "
            + "OR LOWER(p.barcode) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchProducts(@Param("query") String query);

    Optional<Product> findBySku(String sku); // Find a product by SKU

    Optional<Product> findFirstByBarcodeIgnoreCase(String barcode);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productVariations v "
            + "WHERE LOWER(p.barcode) = LOWER(:barcode) "
            + "OR LOWER(p.sku) = LOWER(:barcode) "
            + "OR LOWER(v.subSku) = LOWER(:barcode)")
    List<Product> findByBarcodeOrVariationSubSku(@Param("barcode") String barcode);

    Optional<Product> findTopByOrderByIdDesc(); // Get the most recent product (to generate the next SKU)

    public boolean existsBySku(String sku);

    List<Product> findByStatus(Long status);

    @Query("SELECT p FROM Product p WHERE " + "(LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%')) "
            + "OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%')) "
            + "OR LOWER(p.barcode) LIKE LOWER(CONCAT('%', :query, '%'))) " + "AND p.status = 1")
    List<Product> searchActive(@Param("query") String query);

    @Query("SELECT p FROM Product p WHERE " + "(LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%')) "
            + "OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%')) "
            + "OR LOWER(p.barcode) LIKE LOWER(CONCAT('%', :query, '%'))) " + "AND p.status = 0")
    List<Product> searchInactive(@Param("query") String query);

    @Query("""
			    SELECT new com.backend.Entity.BrandWiseReportDTO(
			        p.brand,

			        COALESCE(SUM(
			            CASE
			                WHEN st.transactionType IN ('di_sale','so_sale')
			                THEN st.quantity
			                ELSE 0
			            END
			        ),0),

			        COALESCE(SUM(
			            CASE
			                WHEN st.transactionType IN ('po_purchase','di_purchase','open_stock','sale_return','product_claimed')
			                THEN st.quantity
			                WHEN st.transactionType IN ('di_sale','so_sale','purchase_return','product_replaced','adjustment')
			                THEN -st.quantity
			                ELSE 0
			            END
			        ),0),

			        COALESCE(
			            SUM(soi.lineTotal) +
			            SUM(dii.lineTotal),
			        0)

			    )
			    FROM Product p
			    LEFT JOIN StockTransaction st ON st.productId = p.id
			    LEFT JOIN SaleSoItem soi ON soi.productId = p.id
			    LEFT JOIN SaleDIItem dii ON dii.productId = p.id
			    GROUP BY p.brand
			""")
    List<BrandWiseReportDTO> getBrandWiseReport();

    @Query("""
			    SELECT new com.backend.Entity.CategoryWiseReportDTO(
			        p.category,

			        COALESCE(SUM(
			            CASE
			                WHEN st.transactionType IN ('di_sale','so_sale')
			                THEN st.quantity
			                ELSE 0
			            END
			        ),0),

			        COALESCE(SUM(
			            CASE
			                WHEN st.transactionType IN ('po_purchase','di_purchase','open_stock','sale_return','product_claimed')
			                THEN st.quantity
			                WHEN st.transactionType IN ('di_sale','so_sale','purchase_return','product_replaced','adjustment')
			                THEN -st.quantity
			                ELSE 0
			            END
			        ),0),

			        COALESCE(
			            SUM(soi.lineTotal) +
			            SUM(dii.lineTotal),
			        0)

			    )
			    FROM Product p
			    LEFT JOIN StockTransaction st ON st.productId = p.id
			    LEFT JOIN SaleSoItem soi ON soi.productId = p.id
			    LEFT JOIN SaleDIItem dii ON dii.productId = p.id
			    GROUP BY p.category
			""")
    List<CategoryWiseReportDTO> getCategoryWiseReport();

}
