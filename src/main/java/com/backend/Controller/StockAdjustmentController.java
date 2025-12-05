package com.backend.Controller;

import com.backend.Entity.StockAdjustment;
import com.backend.Service.StockAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock-adjustments")
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
public class StockAdjustmentController {

    @Autowired
    private StockAdjustmentService stockAdjustmentService;

    // Save a new Stock Adjustment
    @PostMapping("/save")
    public ResponseEntity<StockAdjustment> saveStockAdjustment(@RequestBody StockAdjustment stockAdjustment) {
        try {
            StockAdjustment savedStockAdjustment = stockAdjustmentService.saveStockAdjustment(stockAdjustment);
            return new ResponseEntity<>(savedStockAdjustment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieve all Stock Adjustments
    @GetMapping("/getall")
    public ResponseEntity<List<StockAdjustment>> getAllStockAdjustments() {
        try {
            List<StockAdjustment> stockAdjustments = stockAdjustmentService.getAllStockAdjustments();
            if (stockAdjustments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(stockAdjustments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve a Stock Adjustment by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<StockAdjustment> getStockAdjustmentById(@PathVariable("id") Long id) {
        Optional<StockAdjustment> stockAdjustment = stockAdjustmentService.getStockAdjustmentById(id);
        return stockAdjustment.map(
                stock -> new ResponseEntity<>(stock, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing Stock Adjustment
    @PutMapping("/update/{id}")
    public ResponseEntity<StockAdjustment> updateStockAdjustment(
            @PathVariable("id") Long id, @RequestBody StockAdjustment stockAdjustment) {
        try {
            StockAdjustment updatedStockAdjustment = stockAdjustmentService.updateStockAdjustment(id, stockAdjustment);
            return new ResponseEntity<>(updatedStockAdjustment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Stock Adjustment by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteStockAdjustment(@PathVariable("id") Long id) {
        try {
            stockAdjustmentService.deleteStockAdjustment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
