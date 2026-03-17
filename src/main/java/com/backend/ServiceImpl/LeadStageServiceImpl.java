package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.Entity.LeadStage;
import com.backend.Repository.LeadStageRepo;
import com.backend.Service.LeadStageService;

@Service
public class LeadStageServiceImpl implements LeadStageService {

    @Autowired
    private LeadStageRepo leadStageRepo;

    @Override
    public List<LeadStage> getAllStages() {
        return leadStageRepo.findAll(Sort.by(Sort.Direction.ASC, "orderIndex"));
    }

    @Override
    public LeadStage createStage(LeadStage stage) {
        return leadStageRepo.save(stage);
    }

    @Override
    public LeadStage updateStage(Long id, LeadStage stageDetails) {
        Optional<LeadStage> optionalStage = leadStageRepo.findById(id);
        if (optionalStage.isPresent()) {
            LeadStage stage = optionalStage.get();
            stage.setName(stageDetails.getName());
            stage.setLabel(stageDetails.getLabel());
            stage.setColor(stageDetails.getColor());
            stage.setOrderIndex(stageDetails.getOrderIndex());
            return leadStageRepo.save(stage);
        }
        return null;
    }

    @Override
    public void deleteStage(Long id) {
        leadStageRepo.deleteById(id);
    }
}
