package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Campaign;
import com.backend.Repository.CampaignRepo;
import com.backend.Service.CampaignService;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepo campaignRepo;

    @Override
    public Campaign saveCampaign(Campaign campaign) {
        return campaignRepo.save(campaign);
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepo.findAll();
    }

    @Override
    public Campaign getCampaignById(Long id) {
        return campaignRepo.findById(id).orElse(null);
    }

    @Override
    public Campaign updateCampaign(Long id, Campaign updatedCampaign) {
        Campaign existing = campaignRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(updatedCampaign.getName());
            existing.setType(updatedCampaign.getType());
            existing.setCreatedBy(updatedCampaign.getCreatedBy());
            existing.setCreatedAt(updatedCampaign.getCreatedAt());
            return campaignRepo.save(existing);
        }
        return null;
    }

    @Override
    public void deleteCampaignById(Long id) {
        campaignRepo.deleteById(id);
    }
}
