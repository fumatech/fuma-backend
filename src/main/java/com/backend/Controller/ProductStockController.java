package com.backend.Controller;

import com.backend.Entity.ProductStock;
import com.backend.Service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-stock")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	     },
	    allowCredentials = "true"
	)
public class ProductStockController {

    @Autowired
    private ProductStockService productStockService;

    // Save ProductStock
    @PostMapping("/save")
    public ResponseEntity<?> saveProductStock(@RequestBody List<ProductStock> productStocks) {
        productStockService.addProductStock(productStocks);
        return ResponseEntity.ok("Product stock saved successfully.");
    }

    // Update ProductStock by productId and variationId
    @PutMapping("/update/{productId}/{variationId}")
    public ResponseEntity<ProductStock> updateProductStock(
            @PathVariable Long productId,
            @PathVariable Long variationId,
            @RequestBody ProductStock productStock) {
        ProductStock updatedStock = productStockService.updateProductStock(productId, variationId, productStock);
        return ResponseEntity.ok(updatedStock);
    }

    // Delete ProductStock by productId and variationId
    @DeleteMapping("/delete/{productId}/{variationId}")
    public ResponseEntity<String> deleteProductStock(
            @PathVariable Long productId,
            @PathVariable Long variationId) {
        productStockService.deleteProductStock(productId, variationId);
        return ResponseEntity.ok("Product stock deleted successfully for productId: " + productId + " and variationId: " + variationId);
    }

    // Get ProductStock by productId and variationId
    @GetMapping("/get/{productId}/{variationId}")
    public ResponseEntity<ProductStock> getProductStockByProductIdAndVariationId(
            @PathVariable Long productId,
            @PathVariable Long variationId) {
        ProductStock productStock = productStockService.getProductStockByProductIdAndVariationId(productId, variationId);
        return ResponseEntity.ok(productStock);
    }

    // Get All ProductStock
    @GetMapping("/getall")
    public ResponseEntity<List<ProductStock>> getAllProductStock() {
        List<ProductStock> productStocks = productStockService.getAllProductStock();
        return ResponseEntity.ok(productStocks);
    }

    // Get ProductStock by Product ID
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ProductStock>> getProductStockByProductId(@PathVariable Long productId) {
        List<ProductStock> productStocks = productStockService.getProductStockByProductId(productId);
        return ResponseEntity.ok(productStocks);
    }

    // Get Total Stock by Variation ID
    @GetMapping("/getstock/{variationId}")
    public ResponseEntity<Long> getTotalStockByVariationId(@PathVariable Long variationId) {
        Long totalStock = productStockService.getTotalStockByVariationId(variationId);
        return ResponseEntity.ok(totalStock);
    }
}
