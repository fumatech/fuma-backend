package com.backend.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.Entity.LeadStage;
import com.backend.Repository.LeadStageRepo;

import java.util.Arrays;
import java.util.List;

@Component
public class LeadStageSeeder {

    @Autowired
    private LeadStageRepo leadStageRepo;

    @PostConstruct
    public void seedDefaultStages() {
        if (leadStageRepo.count() == 0) {
            List<LeadStage> defaultStages = Arrays.asList(
                    new LeadStage("NEW", "New Lead", "#4361ee", 1),
                    new LeadStage("CONTACTED", "Contacted", "#7209b7", 2),
                    new LeadStage("IN_DISCUSSION", "In Discussion", "#fb8500", 3),
                    new LeadStage("CONVERTED", "Converted", "#06d6a0", 4),
                    new LeadStage("LOST", "Lost", "#e63946", 5)
            );
            leadStageRepo.saveAll(defaultStages);
            System.out.println("Seeded default custom lead stages into database.");
        }
    }
}
