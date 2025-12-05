package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.TaxGroup;


@Repository
public interface TaxGroupRepo extends JpaRepository<TaxGroup, Long> {
    Optional<TaxGroup> findByName(String name);
}