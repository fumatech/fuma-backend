package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.Quotation;

@Repository
public interface QuotationRepo extends JpaRepository<Quotation, Long> {
    Optional<Quotation> findByQuotationNo(String quotationNo);
    Optional<Quotation> findTopByOrderByIdDesc();
}
