package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.ReferralProfile;

@Repository
public interface ReferralProfileRepo extends JpaRepository<ReferralProfile, Long> {

    Optional<ReferralProfile> findByUserId(Long userId);

    Optional<ReferralProfile> findByReferralCode(String referralCode);

    boolean existsByReferralCode(String referralCode);
}
