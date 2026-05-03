package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.Entity.QRCode;

@Repository
public interface QRCodeRepo extends JpaRepository<QRCode, Long> {

    Optional<QRCode> findByQrCodeValue(String qrCodeValue);

    long countByUsed(Boolean used);

    @Query("SELECT COALESCE(SUM(q.cashbackAmount),0) FROM QRCode q WHERE q.used = true")
    java.math.BigDecimal getTotalDistributedCashback();

    @Query("SELECT q.campaignId, COUNT(q), COALESCE(SUM(CASE WHEN q.used = true THEN 1 ELSE 0 END),0) FROM QRCode q GROUP BY q.campaignId")
    List<Object[]> getCampaignWiseStats();

    List<QRCode> findByBatchId(@Param("batchId") String batchId);
}
