package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.Campaign;
import com.backend.Service.CampaignService;

@RestController
@RequestMapping("/campaign")
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
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @PostMapping("/save")
    public Campaign saveCampaign(@RequestBody Campaign campaign) {
        return campaignService.saveCampaign(campaign);
    }

    @GetMapping("/getall")
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/get/{id}")
    public Campaign getCampaignById(@PathVariable Long id) {
        return campaignService.getCampaignById(id);
    }

    @PutMapping("/update/{id}")
    public Campaign updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign) {
        return campaignService.updateCampaign(id, campaign);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaignById(id);
    }
}
