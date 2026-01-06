package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.AddLeave;

@Repository
public interface AddLeaveRepo extends JpaRepository<AddLeave, Long> {

}
