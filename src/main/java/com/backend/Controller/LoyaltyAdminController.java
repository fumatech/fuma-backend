package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.LoyaltyCampaign;
import com.backend.Service.LoyaltyService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class LoyaltyAdminController {

    @Autowired
    private LoyaltyService loyaltyService;

    @PostMapping("/qr-campaign/save")
    public LoyaltyCampaign saveCampaign(@RequestBody LoyaltyCampaign campaign) {
        return loyaltyService.saveCampaign(campaign);
    }

    @GetMapping("/qr-campaign/getall")
    public List<LoyaltyCampaign> getCampaigns() {
        return loyaltyService.getAllCampaigns();
    }

    @PutMapping("/qr-campaign/toggle")
    public LoyaltyCampaign toggleCampaign(@RequestParam Long campaignId, @RequestParam Boolean enabled) {
        return loyaltyService.toggleCampaign(campaignId, enabled);
    }

    @GetMapping("/qr-report")
    public Map<String, Object> getQrReport() {
        return loyaltyService.getAdminQrReport();
    }
}
