package com.backend.Controller;

import java.util.List;
import java.util.Optional;

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

import com.backend.Entity.Variations;
import com.backend.Service.VariationsService;

import Exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/variations")
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
public class VariationsController {
	 @Autowired
	    private VariationsService variationService;
	 
	 

	    public VariationsController(VariationsService variationService) {
		super();
		this.variationService = variationService;
	}

		@PostMapping("/save")
	    public ResponseEntity<Variations> createVariation(@RequestBody Variations variations) {
	        try {
	            Variations savedVariations = variationService.saveVariations(variations);
	            return new ResponseEntity<>(savedVariations, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	    }

	    @GetMapping("/getall")
	    public ResponseEntity<List<Variations>> getAllVariations() {
	        List<Variations> variationList = variationService.getAll();
	        return new ResponseEntity<>(variationList, HttpStatus.OK);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Variations> getVariationById(@PathVariable("id") Long id) {
	        Optional<Variations> variation = variationService.getById(id);
	        return variation.map(v -> new ResponseEntity<>(v, HttpStatus.OK))
	                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<Variations> updateVariation(@PathVariable("id") Long id, @RequestBody Variations updatedVariation) {
	        try {
	            Variations updated = variationService.updateVariation(id, updatedVariation);
	            return new ResponseEntity<>(updated, HttpStatus.OK);
	        } catch (ResourceNotFoundException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteVariation(@PathVariable("id") Long id) {
	        try {
	            variationService.deleteById(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (ResourceNotFoundException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	}