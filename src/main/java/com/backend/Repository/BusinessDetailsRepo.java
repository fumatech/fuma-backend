package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.BusinessDetails;
@Repository
public interface BusinessDetailsRepo extends JpaRepository<BusinessDetails, Long> {

}
