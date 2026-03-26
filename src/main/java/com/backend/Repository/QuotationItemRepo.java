package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.QuotationItem;

@Repository
public interface QuotationItemRepo extends JpaRepository<QuotationItem, Long> {
    List<QuotationItem> findByQuotation_Id(Long quotationId);
    void deleteByQuotation_Id(Long quotationId);
}
