package com.backend.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.LeadActivity;
import com.backend.Repository.LeadActivityRepo;
import com.backend.Service.LeadActivityService;

@Service
public class LeadActivityServiceImpl implements LeadActivityService {

    @Autowired
    private LeadActivityRepo leadActivityRepo;

    @Override
    public LeadActivity logActivity(Long leadId, String activityType, String description) {
        LeadActivity activity = new LeadActivity();
        activity.setLeadId(leadId);
        activity.setActivityType(activityType);
        activity.setDescription(description);
        activity.setDate(LocalDateTime.now());
        return leadActivityRepo.save(activity);
    }

    @Override
    public List<LeadActivity> getActivitiesByLeadId(Long leadId) {
        return leadActivityRepo.findByLeadIdOrderByDateDesc(leadId);
    }
}
