package com.backend.Service;

import java.util.List;
import java.util.Optional;

import com.backend.Entity.Product;

public interface ProductService {

	Product saveProduct(Product product);

	Product getProductById(Long id);

	List<Product> getAllProducts();

	Product updateProduct(Long id, Product product);

	void deleteProduct(Long id);

	List<Product> searchProducts(String query);

	Optional<Product> findProductDetailsByIdAndVariation(Long productId, Long productVariationId);

	// Method to check if SKU exists
	boolean isSkuExists(String sku);

	// Method to get the next SKU starting from 1000
	String generateNextSku();

	public boolean isSkuUnique(String sku);

	List<Product> getProductsByStatus(Long status);

	Product updateProductStatus(Product product);

	List<Product> searchActiveProducts(String query);

	List<Product> searchInactiveProducts(String query);

}
