package com.backend.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long referrerUserId;
    private Long referredUserId;
    private String referralCode;
    private BigDecimal referrerBonus;
    private BigDecimal referredBonus;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferrerUserId() {
        return referrerUserId;
    }

    public void setReferrerUserId(Long referrerUserId) {
        this.referrerUserId = referrerUserId;
    }

    public Long getReferredUserId() {
        return referredUserId;
    }

    public void setReferredUserId(Long referredUserId) {
        this.referredUserId = referredUserId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public BigDecimal getReferrerBonus() {
        return referrerBonus;
    }

    public void setReferrerBonus(BigDecimal referrerBonus) {
        this.referrerBonus = referrerBonus;
    }

    public BigDecimal getReferredBonus() {
        return referredBonus;
    }

    public void setReferredBonus(BigDecimal referredBonus) {
        this.referredBonus = referredBonus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
