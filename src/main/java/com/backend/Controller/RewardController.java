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

import com.backend.DTO.RewardClaimRequest;
import com.backend.Service.LoyaltyService;

@RestController
@RequestMapping("/reward")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class RewardController {

    @Autowired
    private LoyaltyService loyaltyService;

    @GetMapping("/validate")
    public Map<String, Object> validate(@RequestParam("code") String code) {
        return loyaltyService.validateRewardCode(code);
    }

    @PostMapping("/claim")
    public Map<String, Object> claim(@RequestBody RewardClaimRequest request) {
        return loyaltyService.claimReward(request.getUserId(), request.getCode());
    }
}
