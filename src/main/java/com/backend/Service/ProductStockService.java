package com.backend.Service;

import com.backend.Entity.ProductStock;
import java.util.List;

public interface ProductStockService {
    void addProductStock(List<ProductStock> productStocks); // Insert multiple stocks

    ProductStock updateProductStock(Long productId, Long variationId, ProductStock productStock); // Update a stock by productId and variationId

    void deleteProductStock(Long productId, Long variationId); // Delete a stock by productId and variationId

    ProductStock getProductStockByProductIdAndVariationId(Long productId, Long variationId); // Get a stock by productId and variationId

    List<ProductStock> getAllProductStock(); // Get all stocks

    List<ProductStock> getProductStockByProductId(Long productId); // Get stocks by Product ID

    Long getTotalStockByVariationId(Long variationId); // Get total stock by Variation ID
}
