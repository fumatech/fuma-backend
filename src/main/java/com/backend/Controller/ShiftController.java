package com.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.Shift;
import com.backend.Service.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/shift")
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
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    // Create a new shift
    @PostMapping("/add")
    public ResponseEntity<Shift> saveShift(@RequestBody Shift shift) {
        Shift createdShift = shiftService.saveShift(shift);
        return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
    }

    // Get all shifts
    @GetMapping("/getall")
    public ResponseEntity<List<Shift>> getAllShifts() {
        List<Shift> shifts = shiftService.getAllShifts();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    // Get shift by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Shift> getShiftById(@PathVariable Long id) {
        Shift shift = shiftService.getShiftById(id);
        if (shift != null) {
            return new ResponseEntity<>(shift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update shift by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Shift> updateShift(@PathVariable Long id, @RequestBody Shift updatedShift) {
        Shift shift = shiftService.updateShift(id, updatedShift);
        if (shift != null) {
            return new ResponseEntity<>(shift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete shift by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShiftById(@PathVariable Long id) {
        shiftService.deleteShiftById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
