package com.backend.Service;

import java.util.List;

import com.backend.Entity.Campaign;

public interface CampaignService {
    Campaign saveCampaign(Campaign campaign);
    List<Campaign> getAllCampaigns();
    Campaign getCampaignById(Long id);
    Campaign updateCampaign(Long id, Campaign updatedCampaign);
    void deleteCampaignById(Long id);
}
