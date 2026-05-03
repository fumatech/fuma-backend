package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.LoyaltyCampaign;

@Repository
public interface LoyaltyCampaignRepo extends JpaRepository<LoyaltyCampaign, Long> {
}
