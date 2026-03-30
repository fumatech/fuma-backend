package com.backend.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Lead;

@Repository
public interface LeadRepo extends JpaRepository<Lead, Long> {

	long countByStage(String stage);

	long countByAddedOnBetween(LocalDateTime start, LocalDateTime end);

	long countByStageAndAddedOnBetween(String stage, LocalDateTime start, LocalDateTime end);

	List<Lead> findByAddedOnBetween(LocalDateTime start, LocalDateTime end);

	@Query("SELECT l FROM Lead l WHERE l.followUpDate BETWEEN :start AND :end AND l.followUpStatus = :status")
	List<Lead> findFollowUpsInRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("status") String status);

	@Query("SELECT l FROM Lead l WHERE l.followUpDate < :now AND l.followUpStatus = 'PENDING'")
	List<Lead> findOverdueFollowUps(@Param("now") LocalDateTime now);

	List<Lead> findByFollowUpDateAfterAndFollowUpStatus(LocalDateTime date, String status);

}
