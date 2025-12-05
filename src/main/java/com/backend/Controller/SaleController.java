package com.backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.Sale;
import com.backend.Service.SaleService;

@RestController
@RequestMapping("/sale")
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
public class SaleController {

    @Autowired
    private SaleService saleService;

    // Create a new sale
    @PostMapping("/save")
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        Sale newSale = saleService.createSale(sale);
        return ResponseEntity.ok(newSale);
    }

    // Retrieve all sales
    @GetMapping("/getall")
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    // Retrieve a sale by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        return sale.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing sale by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestBody Sale saleDetails) {
        try {
            Sale updatedSale = saleService.updateSale(id, saleDetails);
            return ResponseEntity.ok(updatedSale);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a sale by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
