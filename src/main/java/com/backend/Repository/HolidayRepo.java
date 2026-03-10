package com.backend.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Holiday;

@Repository
public interface HolidayRepo extends JpaRepository<Holiday, Long> {

    List<Holiday> findByStartDateGreaterThanEqualOrderByStartDateAsc(Date date);

    long countByStartDateGreaterThanEqual(Date date);
}
