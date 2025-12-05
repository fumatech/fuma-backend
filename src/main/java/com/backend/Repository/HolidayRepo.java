package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.Entity.Holiday;

@Repository
public interface HolidayRepo extends JpaRepository<Holiday, Long> {
    // Custom queries if needed can be added here
}
