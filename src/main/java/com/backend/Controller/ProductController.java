package com.backend.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.Product;
import com.backend.Entity.ProductVariations;
import com.backend.Service.ProductService;
import com.backend.Util.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/product")
// @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private FileUploadUtil fileUploadUtil;

	@PostMapping("/save")
	public ResponseEntity<Product> createProduct(@RequestParam("product") String productJson,
			@RequestParam(value = "productImage", required = false) MultipartFile productImage,
			@RequestParam(value = "variationImages", required = false) List<MultipartFile> variationImages)
			throws IOException {

		// Convert productJson to Product object
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(productJson, Product.class);

		// Handle product image upload
		if (productImage != null && !productImage.isEmpty()) {
			String productImagePath = fileUploadUtil.saveFile(productImage.getOriginalFilename(), productImage);
			product.setProductImage(productImagePath);
		}

		// Handle product variations and their images
		if (product.getProductVariations() != null) {
			for (int i = 0; i < product.getProductVariations().size(); i++) {
				ProductVariations variation = product.getProductVariations().get(i);
				if (variationImages != null && i < variationImages.size()) {
					MultipartFile variationImage = variationImages.get(i);
					if (variationImage != null && !variationImage.isEmpty()) {
						String variationImagePath = fileUploadUtil.saveFile(variationImage.getOriginalFilename(),
								variationImage);
						variation.setVariationProductImages(variationImagePath);
					}
				}
			}
		}

		Product savedProduct = productService.saveProduct(product);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam("product") String productJson,
			@RequestParam(value = "productImage", required = false) MultipartFile productImage,
			@RequestParam(value = "variationImages", required = false) List<MultipartFile> variationImages)
			throws IOException {

		Product existingProduct = productService.getProductById(id);
		if (existingProduct == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(productJson, Product.class);
		product.setId(id);

		// Handle product image update
		if (productImage != null && !productImage.isEmpty()) {
			String productImagePath = fileUploadUtil.saveFile(productImage.getOriginalFilename(), productImage);
			product.setProductImage(productImagePath);
		} else {
			product.setProductImage(existingProduct.getProductImage()); // Preserve existing image if not updated
		}

		// Handle product variations
		if (product.getProductVariations() != null) {
			for (int i = 0; i < product.getProductVariations().size(); i++) {
				ProductVariations variation = product.getProductVariations().get(i);

				if (variation.getId() != null) {
					// Existing variation
					ProductVariations existingVariation = existingProduct.getProductVariations().stream()
							.filter(v -> v.getId().equals(variation.getId())).findFirst().orElse(null);

					if (existingVariation != null) {
						// Preserve the existing image if not updated
						if (variationImages != null && i < variationImages.size()) {
							MultipartFile variationImage = variationImages.get(i);
							if (variationImage != null && !variationImage.isEmpty()) {
								String variationImagePath = fileUploadUtil
										.saveFile(variationImage.getOriginalFilename(), variationImage);
								variation.setVariationProductImages(variationImagePath);
							} else {
								// Keep existing image if no new image is provided
								variation.setVariationProductImages(existingVariation.getVariationProductImages());
							}
						} else {
							// No new image provided, keep existing
							variation.setVariationProductImages(existingVariation.getVariationProductImages());
						}
					}
				}
				variation.setProduct(product); // Set product reference
			}
		}

		Product updatedProduct = productService.updateProduct(id, product);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/details")
	public ResponseEntity<Product> getProductDetailsByNameAndVariation(@RequestParam("productId") Long productId,
			@RequestParam("productVariationId") Long productVariationId) {

		return productService.findProductDetailsByIdAndVariation(productId, productVariationId)
				.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/skuExists")
	public ResponseEntity<Map<String, Boolean>> checkSkuExists(@RequestParam String sku) {
		boolean exists = productService.isSkuUnique(sku);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", !exists); // true if exists, false if not
		return ResponseEntity.ok(response);
	}

	@PutMapping("/status/{id}")
	public ResponseEntity<Map<String, Object>> updateProductStatus(@PathVariable Long id, @RequestParam Long status) {
		Product product = productService.getProductById(id);
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		product.setStatus(status);
		productService.updateProductStatus(product); //

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Product status updated successfully");
		response.put("productId", id);
		response.put("status", status);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getallactive")
	public ResponseEntity<List<Product>> getActiveProducts() {
		List<Product> activeProducts = productService.getProductsByStatus(1L);
		return ResponseEntity.ok(activeProducts);
	}

	@GetMapping("/getallinactive")
	public ResponseEntity<List<Product>> getInactiveProducts() {
		List<Product> inactiveProducts = productService.getProductsByStatus(0L);
		return ResponseEntity.ok(inactiveProducts);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
		List<Product> products = productService.searchProducts(query);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/search/active")
	public ResponseEntity<List<Product>> searchActiveProducts(@RequestParam String query) {
		List<Product> products = productService.searchActiveProducts(query);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/search/inactive")
	public ResponseEntity<List<Product>> searchInactiveProducts(@RequestParam String query) {
		List<Product> products = productService.searchInactiveProducts(query);
		return ResponseEntity.ok(products);
	}

}
