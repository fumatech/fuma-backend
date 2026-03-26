package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.QuotationStatusHistory;

@Repository
public interface QuotationStatusHistoryRepo extends JpaRepository<QuotationStatusHistory, Long> {
    List<QuotationStatusHistory> findByQuotation_IdOrderByChangedAtDesc(Long quotationId);
}
