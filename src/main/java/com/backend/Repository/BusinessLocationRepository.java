package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.BusinessLocation;

@Repository
public interface BusinessLocationRepository extends JpaRepository<BusinessLocation, Long> {
}
