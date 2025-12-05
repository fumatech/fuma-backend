package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Campaign;

@Repository
public interface CampaignRepo  extends JpaRepository<Campaign, Long>{

}
