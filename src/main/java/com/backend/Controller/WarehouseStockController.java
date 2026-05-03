package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.WarehouseStock;
import com.backend.Service.WarehouseStockService;

@RestController
@RequestMapping("/warehouse-stock")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class WarehouseStockController {

	@Autowired
	private WarehouseStockService warehouseStockService;

	@GetMapping("/by-warehouse/{warehouseId}")
	public ResponseEntity<List<WarehouseStock>> byWarehouse(@PathVariable Long warehouseId) {
		return ResponseEntity.ok(warehouseStockService.getByWarehouseId(warehouseId));
	}

	@GetMapping("/getbywarehouse/{warehouseId}")
	public ResponseEntity<List<WarehouseStock>> getByWarehouse(@PathVariable Long warehouseId) {
		return ResponseEntity.ok(warehouseStockService.getByWarehouseId(warehouseId));
	}

	@GetMapping("/getall")
	public ResponseEntity<List<WarehouseStock>> getAll(@RequestParam(required = false) Long warehouseId) {
		if (warehouseId == null) {
			return new ResponseEntity<>(List.of(), HttpStatus.OK);
		}
		return ResponseEntity.ok(warehouseStockService.getByWarehouseId(warehouseId));
	}

	@PostMapping("/current-stock/bulk")
	public ResponseEntity<Map<String, Integer>> getBulkCurrentStock(
			@RequestParam Long warehouseId,
			@RequestBody List<Map<String, Long>> requests) {
		return ResponseEntity.ok(warehouseStockService.getBulkCurrentStock(warehouseId, requests));
	}
}

