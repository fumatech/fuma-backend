  package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.PayComponent;
@Repository
public interface PayComponentRepo extends JpaRepository<PayComponent, Long> {

}
