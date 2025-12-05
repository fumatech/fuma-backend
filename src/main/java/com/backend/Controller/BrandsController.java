package com.backend.Controller;

import com.backend.Entity.Brands;
import com.backend.Service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
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
public class BrandsController {

    @Autowired
    private BrandsService brandsService;

    // API to save a new brand
    @PostMapping("/save")
    public ResponseEntity<Brands> saveBrand(@RequestBody Brands brand) {
        Brands savedBrand = brandsService.saveBrands(brand);
        return new ResponseEntity<>(savedBrand, HttpStatus.CREATED);
    }

    // API to update an existing brand
    @PutMapping("/update/{id}")
    public ResponseEntity<Brands> updateBrand(@PathVariable Long id, @RequestBody Brands brand) {
        Brands existingBrand = brandsService.getById(id);
        if (existingBrand != null) {
            brand.setId(id); // Ensure the ID is set for the update operation
            Brands updatedBrand = brandsService.saveBrands(brand);
            return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // API to get a brand by ID
    @GetMapping("/{id}")
    public ResponseEntity<Brands> getById(@PathVariable Long id) {
        Brands brand = brandsService.getById(id);
        if (brand != null) {
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // API to get all brands
    @GetMapping("/getall")
    public ResponseEntity<List<Brands>> getAllBrands() {
        List<Brands> brandsList = brandsService.getAllBrands();
        return new ResponseEntity<>(brandsList, HttpStatus.OK);
    }

    // API to delete a brand by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Brands brand = brandsService.getById(id);
        if (brand != null) {
            brandsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
