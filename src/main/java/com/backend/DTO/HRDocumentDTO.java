package com.backend.DTO;

public class HRDocumentDTO {

    private Long id;
    private String fileName;
    private String uploadedBy;
    private String documentType;
    private String createdAt;

    public HRDocumentDTO(Long id, String fileName, String uploadedBy, String documentType, String createdAt) {
        this.id = id;
        this.fileName = fileName;
        this.uploadedBy = uploadedBy;
        this.documentType = documentType;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
