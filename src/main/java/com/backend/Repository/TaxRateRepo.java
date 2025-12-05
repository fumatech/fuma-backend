package com.backend.Repository;

import com.backend.Entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRateRepo extends JpaRepository<TaxRate, Long> {
}
