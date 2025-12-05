package com.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.Entity.Tax;
import com.backend.Service.TaxService;

import java.util.List;

@RestController
@RequestMapping("/tax")
//@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:3001" })
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
public class TaxController {

	@Autowired
	private TaxService taxService;

	@GetMapping("/getall")
	public ResponseEntity<List<Tax>> getAllTaxes() {
		return ResponseEntity.ok(taxService.getAllTaxes());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Tax> getTaxById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(taxService.getTaxById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/save")
	public ResponseEntity<Tax> createTax(@RequestBody Tax tax) {
		return ResponseEntity.ok(taxService.createTax(tax));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Tax> updateTax(@PathVariable Long id, @RequestBody Tax taxDetails) {
		try {
			return ResponseEntity.ok(taxService.updateTax(id, taxDetails));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTax(@PathVariable Long id) {
		try {
			taxService.deleteTax(id);
			return ResponseEntity.ok("Tax deleted successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
