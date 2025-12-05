package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Leave;

@Repository
public interface LeaveRepo extends JpaRepository<Leave, Long> {

}
