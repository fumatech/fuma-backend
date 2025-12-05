package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PayRoll;

@Repository
public interface PayRollRepo extends JpaRepository<PayRoll, Long> {

}
