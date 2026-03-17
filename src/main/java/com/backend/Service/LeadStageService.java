package com.backend.Service;

import java.util.List;

import com.backend.Entity.LeadStage;

public interface LeadStageService {
    List<LeadStage> getAllStages();
    LeadStage createStage(LeadStage stage);
    LeadStage updateStage(Long id, LeadStage stage);
    void deleteStage(Long id);
}
