package com.backend.Controller;

import com.backend.Entity.Designation;
import com.backend.Service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/designation")
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
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    // Create a new designation
    @PostMapping("/add")
    public ResponseEntity<Designation> createDesignation(@RequestBody Designation designation) {
        Designation savedDesignation = designationService.saveDesignation(designation);
        return new ResponseEntity<>(savedDesignation, HttpStatus.CREATED);
    }

    // Get all designations
    @GetMapping("/getall")
    public ResponseEntity<List<Designation>> getAllDesignations() {
        List<Designation> designations = designationService.getAllDesignations();
        return new ResponseEntity<>(designations, HttpStatus.OK);
    }

    // Get a designation by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Designation> getDesignationById(@PathVariable Long id) {
        try {
            Designation designation = designationService.getDesignationById(id);
            return new ResponseEntity<>(designation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update a designation
    @PutMapping("/update/{id}")
    public ResponseEntity<Designation> updateDesignation(@PathVariable Long id, @RequestBody Designation designation) {
        try {
            Designation updatedDesignation = designationService.updateDesignation(id, designation);
            return new ResponseEntity<>(updatedDesignation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a designation by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDesignationById(@PathVariable Long id) {
        try {
            designationService.deleteDesignationById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
