package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.Entity.AddLeave;

@Repository
public interface AddLeaveRepo extends JpaRepository<AddLeave, Long> {

    @Query("SELECT a.status, COUNT(a) FROM AddLeave a GROUP BY a.status")
    List<Object[]> countByStatusGroup();

    @Query("SELECT COUNT(a) FROM AddLeave a WHERE a.status = 1 AND a.startDate <= CURRENT_TIMESTAMP AND a.endDate >= CURRENT_TIMESTAMP")
    long countCurrentlyOnLeave();

    @Query("SELECT COUNT(a) FROM AddLeave a WHERE a.status = 0")
    long countPending();

}
