package com.backend.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.DTO.ReferralApplyRequest;
import com.backend.Service.LoyaltyService;

@RestController
@RequestMapping("/referral")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class ReferralController {

    @Autowired
    private LoyaltyService loyaltyService;

    @PostMapping("/apply")
    public Map<String, Object> apply(@RequestBody ReferralApplyRequest request) {
        return loyaltyService.applyReferral(request);
    }

    @GetMapping("/code")
    public Map<String, String> getCode(@RequestParam Long userId) {
        String code = loyaltyService.getOrCreateReferralCode(userId);
        return Map.of("referralCode", code);
    }
}
