package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Entity.HRDocument;
import com.backend.Entity.HRDocumentType;

@Repository
public interface HRDocumentRepo extends JpaRepository<HRDocument, Long> {

    List<HRDocument> findByActiveTrueOrderByCreatedAtDesc();

    List<HRDocument> findByEmployeeIdAndActiveTrueOrderByCreatedAtDesc(Long employeeId);

    List<HRDocument> findByEmployeeIdAndDocumentTypeAndActiveTrueOrderByCreatedAtDesc(Long employeeId, HRDocumentType documentType);

    List<HRDocument> findByUploadedByAndActiveTrueOrderByCreatedAtDesc(Long uploadedBy);
}
