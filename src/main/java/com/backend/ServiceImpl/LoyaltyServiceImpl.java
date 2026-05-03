package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.DTO.QrGenerateRequest;
import com.backend.DTO.QrScanRequest;
import com.backend.DTO.ReferralApplyRequest;
import com.backend.Entity.LoyaltyCampaign;
import com.backend.Entity.QRCode;
import com.backend.Entity.Referral;
import com.backend.Entity.ReferralProfile;
import com.backend.Entity.Wallet;
import com.backend.Entity.WalletTransaction;
import com.backend.Repository.LoyaltyCampaignRepo;
import com.backend.Repository.QRCodeRepo;
import com.backend.Repository.ReferralProfileRepo;
import com.backend.Repository.ReferralRepo;
import com.backend.Repository.UserRepo;
import com.backend.Repository.WalletRepo;
import com.backend.Repository.WalletTransactionRepo;
import com.backend.Service.LoyaltyService;

@Service
public class LoyaltyServiceImpl implements LoyaltyService {

    private static final BigDecimal DEFAULT_REFERRER_BONUS = new BigDecimal("50.00");
    private static final BigDecimal DEFAULT_REFERRED_BONUS = new BigDecimal("50.00");
    private static final String REWARD_URL_PREFIX = "https://fuma.com/reward?code=";

    @Autowired
    private QRCodeRepo qrCodeRepo;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WalletTransactionRepo walletTransactionRepo;

    @Autowired
    private ReferralProfileRepo referralProfileRepo;

    @Autowired
    private ReferralRepo referralRepo;

    @Autowired
    private LoyaltyCampaignRepo loyaltyCampaignRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<QRCode> generateQRCodes(QrGenerateRequest request) {
        int count = request.getCount() == null || request.getCount() < 1 ? 1 : request.getCount();
        String batchId = request.getBatchId() == null || request.getBatchId().isBlank()
                ? "BATCH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()
                : request.getBatchId();

        LocalDateTime now = LocalDateTime.now();
        BigDecimal cashbackAmount = request.getCashbackAmount();
        LocalDateTime expiry = request.getExpiryDate();
        Long campaignId = request.getCampaignId();

        if (campaignId != null) {
            LoyaltyCampaign campaign = loyaltyCampaignRepo.findById(campaignId)
                    .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));
            if (Boolean.FALSE.equals(campaign.getEnabled())) {
                throw new IllegalArgumentException("Campaign is disabled");
            }
            if (campaign.getValidTo() != null && campaign.getValidTo().isBefore(now)) {
                throw new IllegalArgumentException("Campaign has expired");
            }
            if (cashbackAmount == null) {
                cashbackAmount = campaign.getCashbackAmount();
            }
            if (expiry == null) {
                expiry = campaign.getValidTo();
            }
        }

        if (cashbackAmount == null || cashbackAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cashback amount must be greater than zero");
        }

        List<QRCode> qrCodes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            QRCode qrCode = new QRCode();
            qrCode.setQrCodeValue("FUMA-QR-" + UUID.randomUUID().toString().replace("-", "").toUpperCase());
            qrCode.setRewardUrl(REWARD_URL_PREFIX + qrCode.getQrCodeValue());
            qrCode.setProductId(request.getProductId());
            qrCode.setCampaignId(campaignId);
            qrCode.setCashbackAmount(cashbackAmount);
            qrCode.setExpiryDate(expiry);
            qrCode.setUsed(false);
            qrCode.setBatchId(batchId);
            qrCode.setCreatedAt(now);
            qrCodes.add(qrCode);
        }

        return qrCodeRepo.saveAll(qrCodes);
    }

    @Override
    public Map<String, Object> validateRewardCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code is required");
        }

        QRCode qrCode = qrCodeRepo.findByQrCodeValue(code)
                .orElseThrow(() -> new IllegalArgumentException("QR code not found"));

        if (Boolean.TRUE.equals(qrCode.getUsed())) {
            throw new IllegalArgumentException("QR code already used");
        }

        if (qrCode.getExpiryDate() != null && qrCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("QR code expired");
        }

        if (qrCode.getCampaignId() != null) {
            Optional<LoyaltyCampaign> campaignOpt = loyaltyCampaignRepo.findById(qrCode.getCampaignId());
            if (campaignOpt.isPresent()) {
                LoyaltyCampaign campaign = campaignOpt.get();
                if (Boolean.FALSE.equals(campaign.getEnabled())) {
                    throw new IllegalArgumentException("Campaign disabled");
                }
                if (campaign.getValidTo() != null && campaign.getValidTo().isBefore(LocalDateTime.now())) {
                    throw new IllegalArgumentException("Campaign expired");
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("valid", true);
        response.put("code", qrCode.getQrCodeValue());
        response.put("cashbackAmount", qrCode.getCashbackAmount());
        response.put("expiryDate", qrCode.getExpiryDate());
        return response;
    }

    @Override
    public Map<String, Object> claimReward(Long userId, String code) {
        validateRewardCode(code);
        QrScanRequest request = new QrScanRequest();
        request.setUserId(userId);
        request.setQrCodeValue(code);
        return scanQRCode(request);
    }

    @Override
    public Map<String, Object> scanQRCode(QrScanRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (request.getQrCodeValue() == null || request.getQrCodeValue().isBlank()) {
            throw new IllegalArgumentException("qrCodeValue is required");
        }

        QRCode qrCode = qrCodeRepo.findByQrCodeValue(request.getQrCodeValue())
                .orElseThrow(() -> new IllegalArgumentException("QR code not found"));

        if (Boolean.TRUE.equals(qrCode.getUsed())) {
            throw new IllegalArgumentException("QR code already used");
        }
        if (qrCode.getExpiryDate() != null && qrCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("QR code expired");
        }

        if (qrCode.getCampaignId() != null) {
            Optional<LoyaltyCampaign> campaignOpt = loyaltyCampaignRepo.findById(qrCode.getCampaignId());
            if (campaignOpt.isPresent()) {
                LoyaltyCampaign campaign = campaignOpt.get();
                if (Boolean.FALSE.equals(campaign.getEnabled())) {
                    throw new IllegalArgumentException("Campaign disabled");
                }
                if (campaign.getValidTo() != null && campaign.getValidTo().isBefore(LocalDateTime.now())) {
                    throw new IllegalArgumentException("Campaign expired");
                }
            }
        }

        Wallet wallet = getWallet(request.getUserId());

        qrCode.setUsed(true);
        qrCode.setUsedByUserId(request.getUserId());
        qrCode.setUsedAt(LocalDateTime.now());
        qrCodeRepo.save(qrCode);

        wallet.setBalance(wallet.getBalance().add(qrCode.getCashbackAmount()));
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepo.save(wallet);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setUserId(request.getUserId());
        transaction.setAmount(qrCode.getCashbackAmount());
        transaction.setTransactionType("CREDIT");
        transaction.setSource("QR_CASHBACK");
        transaction.setReferenceCode(qrCode.getQrCodeValue());
        transaction.setCreatedAt(LocalDateTime.now());
        walletTransactionRepo.save(transaction);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cashback credited successfully");
        response.put("qrCode", qrCode.getQrCodeValue());
        response.put("cashbackAmount", qrCode.getCashbackAmount());
        response.put("walletBalance", wallet.getBalance());
        return response;
    }

    @Override
    public Wallet getWallet(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }

        return walletRepo.findByUserId(userId).orElseGet(() -> {
            Wallet wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setBalance(BigDecimal.ZERO);
            wallet.setUpdatedAt(LocalDateTime.now());
            return walletRepo.save(wallet);
        });
    }

    @Override
    public List<WalletTransaction> getWalletTransactions(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        return walletTransactionRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Map<String, Object> applyReferral(ReferralApplyRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (request.getReferralCode() == null || request.getReferralCode().isBlank()) {
            throw new IllegalArgumentException("referralCode is required");
        }

        if (!userRepo.existsById(request.getUserId())) {
            throw new IllegalArgumentException("User not found");
        }

        ReferralProfile referrerProfile = referralProfileRepo.findByReferralCode(request.getReferralCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid referral code"));

        if (referrerProfile.getUserId().equals(request.getUserId())) {
            throw new IllegalArgumentException("Self referral is not allowed");
        }

        if (referralRepo.findByReferredUserId(request.getUserId()).isPresent()) {
            throw new IllegalArgumentException("Referral already applied for this user");
        }

        getOrCreateReferralCode(request.getUserId());

        Referral referral = new Referral();
        referral.setReferrerUserId(referrerProfile.getUserId());
        referral.setReferredUserId(request.getUserId());
        referral.setReferralCode(request.getReferralCode());
        referral.setReferrerBonus(DEFAULT_REFERRER_BONUS);
        referral.setReferredBonus(DEFAULT_REFERRED_BONUS);
        referral.setCreatedAt(LocalDateTime.now());
        referralRepo.save(referral);

        creditWallet(referrerProfile.getUserId(), DEFAULT_REFERRER_BONUS, "REFERRAL", "REF-" + referral.getId());
        creditWallet(request.getUserId(), DEFAULT_REFERRED_BONUS, "REFERRAL", "REF-" + referral.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Referral applied successfully");
        response.put("referrerBonus", DEFAULT_REFERRER_BONUS);
        response.put("newUserBonus", DEFAULT_REFERRED_BONUS);
        return response;
    }

    @Override
    public String getOrCreateReferralCode(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }

        if (!userRepo.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<ReferralProfile> existing = referralProfileRepo.findByUserId(userId);
        if (existing.isPresent()) {
            return existing.get().getReferralCode();
        }

        String referralCode;
        do {
            referralCode = "FUMA" + userId + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        } while (referralProfileRepo.existsByReferralCode(referralCode));

        ReferralProfile profile = new ReferralProfile();
        profile.setUserId(userId);
        profile.setReferralCode(referralCode);
        profile.setCreatedAt(LocalDateTime.now());
        referralProfileRepo.save(profile);

        return referralCode;
    }

    @Override
    public LoyaltyCampaign saveCampaign(LoyaltyCampaign campaign) {
        if (campaign.getCreatedAt() == null) {
            campaign.setCreatedAt(LocalDateTime.now());
        }
        if (campaign.getEnabled() == null) {
            campaign.setEnabled(true);
        }
        return loyaltyCampaignRepo.save(campaign);
    }

    @Override
    public List<LoyaltyCampaign> getAllCampaigns() {
        return loyaltyCampaignRepo.findAll();
    }

    @Override
    public LoyaltyCampaign toggleCampaign(Long campaignId, Boolean enabled) {
        LoyaltyCampaign campaign = loyaltyCampaignRepo.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));
        campaign.setEnabled(enabled);
        return loyaltyCampaignRepo.save(campaign);
    }

    @Override
    public Map<String, Object> getAdminQrReport() {
        long total = qrCodeRepo.count();
        long used = qrCodeRepo.countByUsed(true);
        long unused = qrCodeRepo.countByUsed(false);

        long expired = qrCodeRepo.findAll().stream()
                .filter(q -> q.getExpiryDate() != null && q.getExpiryDate().isBefore(LocalDateTime.now()))
                .count();

        BigDecimal distributed = qrCodeRepo.getTotalDistributedCashback();

        List<Map<String, Object>> campaignStats = new ArrayList<>();
        for (Object[] row : qrCodeRepo.getCampaignWiseStats()) {
            Map<String, Object> item = new HashMap<>();
            item.put("campaignId", row[0]);
            item.put("total", row[1]);
            item.put("used", row[2]);
            campaignStats.add(item);
        }

        Map<String, Object> report = new HashMap<>();
        report.put("totalScans", used);
        report.put("totalQr", total);
        report.put("usedQr", used);
        report.put("unusedQr", unused);
        report.put("expiredQr", expired);
        report.put("cashbackDistribution", distributed);
        report.put("campaignStats", campaignStats);
        return report;
    }

    private void creditWallet(Long userId, BigDecimal amount, String source, String referenceCode) {
        Wallet wallet = getWallet(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepo.save(wallet);

        WalletTransaction txn = new WalletTransaction();
        txn.setUserId(userId);
        txn.setAmount(amount);
        txn.setTransactionType("CREDIT");
        txn.setSource(source);
        txn.setReferenceCode(referenceCode);
        txn.setCreatedAt(LocalDateTime.now());
        walletTransactionRepo.save(txn);
    }
}
