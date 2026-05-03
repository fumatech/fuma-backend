package com.backend.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.DTO.AuthLoginRequest;
import com.backend.DTO.AuthResponse;
import com.backend.DTO.AuthSignupRequest;
import com.backend.DTO.ReferralApplyRequest;
import com.backend.Entity.User;
import com.backend.Entity.Wallet;
import com.backend.Repository.UserRepo;
import com.backend.Service.LoyaltyService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoyaltyService loyaltyService;

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody AuthSignupRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("password is required");
        }

        Optional<User> existing = userRepo.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setFirstname(request.getFirstname() == null ? "Customer" : request.getFirstname());
        user.setLastname(request.getLastname() == null ? "" : request.getLastname());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setUsername(request.getEmail().trim().toLowerCase());
        user.setPassword(request.getPassword());
        user.setIsActive(true);
        user.setAllowLogin(true);

        User saved = userRepo.save(user);

        if (request.getReferralCode() != null && !request.getReferralCode().isBlank()) {
            ReferralApplyRequest referralApplyRequest = new ReferralApplyRequest();
            referralApplyRequest.setUserId(saved.getId());
            referralApplyRequest.setReferralCode(request.getReferralCode().trim());
            loyaltyService.applyReferral(referralApplyRequest);
        }

        String referralCode = loyaltyService.getOrCreateReferralCode(saved.getId());
        Wallet wallet = loyaltyService.getWallet(saved.getId());

        AuthResponse response = new AuthResponse();
        response.setUserId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setFirstname(saved.getFirstname());
        response.setLastname(saved.getLastname());
        response.setReferralCode(referralCode);
        response.setWalletBalance(wallet.getBalance());
        response.setMessage("Signup successful");
        return response;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthLoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("password is required");
        }

        User user = userRepo.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new IllegalArgumentException("Account inactive");
        }

        String referralCode = loyaltyService.getOrCreateReferralCode(user.getId());
        Wallet wallet = loyaltyService.getWallet(user.getId());

        AuthResponse response = new AuthResponse();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setReferralCode(referralCode);
        response.setWalletBalance(wallet.getBalance());
        response.setMessage("Login successful");
        return response;
    }
}
