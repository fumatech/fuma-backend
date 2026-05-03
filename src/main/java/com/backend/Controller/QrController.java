package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.DTO.QrGenerateRequest;
import com.backend.DTO.QrScanRequest;
import com.backend.Entity.QRCode;
import com.backend.Service.LoyaltyService;

@RestController
@RequestMapping("/qr")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
    "http://www.fusionmastertech.com", "https://www.fusionmastertech.com"}, allowCredentials = "true")
public class QrController {

    @Autowired
    private LoyaltyService loyaltyService;

    @PostMapping("/generate")
    public List<QRCode> generate(@RequestBody QrGenerateRequest request) {
        return loyaltyService.generateQRCodes(request);
    }

    @PostMapping("/scan")
    public Map<String, Object> scan(@RequestBody QrScanRequest request) {
        return loyaltyService.scanQRCode(request);
    }
}
