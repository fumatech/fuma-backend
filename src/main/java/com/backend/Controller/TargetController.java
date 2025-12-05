package com.backend.Controller;

import com.backend.Entity.Target;
import com.backend.Service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/target")
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
public class TargetController {

    private final TargetService targetService;

    @Autowired
    public TargetController(TargetService targetService) {
        this.targetService = targetService;
    }

    // Add a new Target
    @PostMapping("/add")
    public ResponseEntity<Target> addTarget(@RequestBody Target target) {
        Target createdTarget = targetService.saveTarget(target);
        return new ResponseEntity<>(createdTarget, HttpStatus.CREATED);
    }

    // Get all Targets
    @GetMapping("/getall")
    public ResponseEntity<List<Target>> getAllTargets() {
        List<Target> targets = targetService.getAllTargets();
        return new ResponseEntity<>(targets, HttpStatus.OK);
    }

    // Get a Target by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Target> getTargetById(@PathVariable("id") Long id) {
        Target target = targetService.getTargetById(id);
        if (target == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(target, HttpStatus.OK);
    }

    // Update an existing Target
    @PutMapping("/update/{id}")
    public ResponseEntity<Target> updateTarget(@PathVariable("id") Long id, @RequestBody Target updatedTarget) {
        Target target = targetService.updateTarget(id, updatedTarget);
        if (target == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(target, HttpStatus.OK);
    }

    // Delete a Target by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTarget(@PathVariable("id") Long id) {
        boolean isDeleted = targetService.deleteTargetById(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
