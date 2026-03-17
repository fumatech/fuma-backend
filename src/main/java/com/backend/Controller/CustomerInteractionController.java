package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.CustomerInteraction;
import com.backend.Service.CustomerInteractionService;

@RestController
@CrossOrigin("*")
@RequestMapping("/interactions")
public class CustomerInteractionController {

    @Autowired
    private CustomerInteractionService interactionService;

    @PostMapping
    public ResponseEntity<CustomerInteraction> saveInteraction(@RequestBody CustomerInteraction interaction) {
        return ResponseEntity.ok(interactionService.saveInteraction(interaction));
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<CustomerInteraction>> getInteractionsByLeadId(@PathVariable Long leadId) {
        return ResponseEntity.ok(interactionService.getInteractionsByLeadId(leadId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerInteraction>> getInteractionsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(interactionService.getInteractionsByCustomerId(customerId));
    }

    @GetMapping("/salesperson/{salespersonId}")
    public ResponseEntity<List<CustomerInteraction>> getInteractionsBySalespersonId(@PathVariable Long salespersonId) {
        return ResponseEntity.ok(interactionService.getInteractionsBySalespersonId(salespersonId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerInteraction>> getAllInteractions() {
        return ResponseEntity.ok(interactionService.getAllInteractions());
    }
}
