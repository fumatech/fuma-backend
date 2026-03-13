package com.backend.Service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.HRDocument;

public interface HRDocumentService {

    HRDocument addDocument(Long requesterUserId, Long employeeId, String documentType, String title, String description,
            MultipartFile file);

    HRDocument updateDocument(Long requesterUserId, Long id, Long employeeId, String documentType, String title,
            String description, MultipartFile file);

    List<HRDocument> getAllDocuments(Long requesterUserId);

    HRDocument getDocumentById(Long requesterUserId, Long id);

    List<HRDocument> getDocumentsByEmployee(Long requesterUserId, Long employeeId);

    List<HRDocument> getMyDocuments(Long requesterUserId);

    boolean softDelete(Long requesterUserId, Long id);

    Resource getDownloadResource(Long requesterUserId, Long id);

    String getDownloadFileName(Long requesterUserId, Long id);
}
