package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.LeadActivity;

@Repository
public interface LeadActivityRepo extends JpaRepository<LeadActivity, Long> {
    List<LeadActivity> findByLeadIdOrderByDateDesc(Long leadId);
}
