package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Variations;

@Repository
public interface VariationsRepo extends JpaRepository<Variations, Long> {

}
