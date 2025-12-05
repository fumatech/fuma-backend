package com.backend.Controller;

import com.backend.Entity.Units;
import com.backend.Service.UnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
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
public class UnitsController {

    @Autowired
    private UnitsService unitsService;

    // API to save a new unit
    @PostMapping("/save")
    public ResponseEntity<Units> saveUnits(@RequestBody Units units) {
        Units savedUnit = unitsService.saveUnits(units);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
    }

    // API to update an existing unit
    @PutMapping("/update/{id}")
    public ResponseEntity<Units> updateUnits(@PathVariable Long id, @RequestBody Units units) {
        Units existingUnit = unitsService.getById(id);
        if (existingUnit != null) {
            units.setId(id); // Ensure the ID is set for the update operation
            Units updatedUnit = unitsService.saveUnits(units);
            return new ResponseEntity<>(updatedUnit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // API to get a unit by ID
    @GetMapping("/{id}")
    public ResponseEntity<Units> getById(@PathVariable Long id) {
        Units unit = unitsService.getById(id);
        if (unit != null) {
            return new ResponseEntity<>(unit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // API to get all units
    @GetMapping("/getall")
    public ResponseEntity<List<Units>> getAllUnits() {
        List<Units> unitsList = unitsService.getAllUnits();
        return new ResponseEntity<>(unitsList, HttpStatus.OK);
    }

    // API to delete a unit by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Units unit = unitsService.getById(id);
        if (unit != null) {
            unitsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
