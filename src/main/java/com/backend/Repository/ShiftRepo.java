package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Shift;
@Repository
public interface ShiftRepo extends JpaRepository<Shift, Long> {

}
