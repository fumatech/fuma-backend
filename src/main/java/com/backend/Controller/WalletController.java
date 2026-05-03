package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.Wallet;
import com.backend.Entity.WalletTransaction;
import com.backend.Service.LoyaltyService;

@RestController
@RequestMapping("/wallet")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class WalletController {

    @Autowired
    private LoyaltyService loyaltyService;

    @GetMapping
    public Wallet getWallet(@RequestParam Long userId) {
        return loyaltyService.getWallet(userId);
    }

    @GetMapping("/transactions")
    public List<WalletTransaction> getTransactions(@RequestParam Long userId) {
        return loyaltyService.getWalletTransactions(userId);
    }
}
