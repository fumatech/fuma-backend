package com.backend.Service;

import java.util.List;
import java.util.Map;

import com.backend.DTO.QrGenerateRequest;
import com.backend.DTO.QrScanRequest;
import com.backend.DTO.ReferralApplyRequest;
import com.backend.Entity.LoyaltyCampaign;
import com.backend.Entity.QRCode;
import com.backend.Entity.Wallet;
import com.backend.Entity.WalletTransaction;

public interface LoyaltyService {

    List<QRCode> generateQRCodes(QrGenerateRequest request);

    Map<String, Object> validateRewardCode(String code);

    Map<String, Object> claimReward(Long userId, String code);

    Map<String, Object> scanQRCode(QrScanRequest request);

    Wallet getWallet(Long userId);

    List<WalletTransaction> getWalletTransactions(Long userId);

    Map<String, Object> applyReferral(ReferralApplyRequest request);

    String getOrCreateReferralCode(Long userId);

    LoyaltyCampaign saveCampaign(LoyaltyCampaign campaign);

    List<LoyaltyCampaign> getAllCampaigns();

    LoyaltyCampaign toggleCampaign(Long campaignId, Boolean enabled);

    Map<String, Object> getAdminQrReport();
}
