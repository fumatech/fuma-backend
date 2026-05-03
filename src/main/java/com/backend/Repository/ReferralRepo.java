package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Referral;

@Repository
public interface ReferralRepo extends JpaRepository<Referral, Long> {

    Optional<Referral> findByReferredUserId(Long referredUserId);
}
