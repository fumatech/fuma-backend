package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.StockTransfer;
import com.backend.Service.StockTransferService;

@RestController
@RequestMapping("/stock-transfer")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class StockTransferController {

	@Autowired
	private StockTransferService stockTransferService;

	@PostMapping("/save")
	public ResponseEntity<StockTransfer> save(@RequestBody StockTransfer stockTransfer) {
		return new ResponseEntity<>(stockTransferService.save(stockTransfer), HttpStatus.CREATED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<StockTransfer> getById(@PathVariable Long id) {
		return ResponseEntity.ok(stockTransferService.getById(id));
	}

	@GetMapping("/getall")
	public ResponseEntity<List<StockTransfer>> getAll() {
		return ResponseEntity.ok(stockTransferService.getAll());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<StockTransfer> update(@PathVariable Long id, @RequestBody StockTransfer stockTransfer) {

		return ResponseEntity.ok(stockTransferService.update(id, stockTransfer));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		stockTransferService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
