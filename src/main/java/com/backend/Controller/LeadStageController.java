package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.LeadStage;
import com.backend.Service.LeadStageService;

@RestController
@RequestMapping("/lead-stage")
@CrossOrigin(origins = "*") // Assuming standard permissive CORS for this CRM controller
public class LeadStageController {

    @Autowired
    private LeadStageService leadStageService;

    @GetMapping("/getall")
    public ResponseEntity<List<LeadStage>> getAllStages() {
        return ResponseEntity.ok(leadStageService.getAllStages());
    }

    @PostMapping("/create")
    public ResponseEntity<LeadStage> createStage(@RequestBody LeadStage stage) {
        return ResponseEntity.ok(leadStageService.createStage(stage));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeadStage> updateStage(@PathVariable Long id, @RequestBody LeadStage stage) {
        LeadStage updated = leadStageService.updateStage(id, stage);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        leadStageService.deleteStage(id);
        return ResponseEntity.noContent().build();
    }
}
