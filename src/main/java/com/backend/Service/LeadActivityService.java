package com.backend.Service;

import java.util.List;
import com.backend.Entity.LeadActivity;

public interface LeadActivityService {
    LeadActivity logActivity(Long leadId, String activityType, String description);
    List<LeadActivity> getActivitiesByLeadId(Long leadId);
}
