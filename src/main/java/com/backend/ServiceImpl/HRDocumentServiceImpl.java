package com.backend.ServiceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.HRDocument;
import com.backend.Entity.HRDocumentType;
import com.backend.Entity.Permission;
import com.backend.Entity.Role;
import com.backend.Entity.User;
import com.backend.Repository.HRDocumentRepo;
import com.backend.Repository.UserRepo;
import com.backend.Service.HRDocumentService;
import com.backend.Util.FileUploadUtil;

@Service
public class HRDocumentServiceImpl implements HRDocumentService {

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(
            Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx"));
    private static final long MAX_FILE_SIZE_BYTES = 10L * 1024L * 1024L;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private HRDocumentRepo hrDocumentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Override
    public HRDocument addDocument(Long requesterUserId, Long employeeId, String documentType, String title, String description,
            MultipartFile file) {
        User requester = getUserOrThrow(requesterUserId);
        validateManageAccess(requester);
        validateEmployeeExists(employeeId);
        validateFile(file);

        String cleanedOriginalName = sanitizeFileName(file.getOriginalFilename());
        String extension = getFileExtension(cleanedOriginalName);
        String storedFileName = System.currentTimeMillis() + "_" + cleanedOriginalName.replace(" ", "_");

        try {
            String path = fileUploadUtil.saveFile(storedFileName, file);

            HRDocument document = new HRDocument();
            document.setEmployeeId(employeeId);
            document.setDocumentType(parseDocumentType(documentType));
            document.setTitle((title == null || title.isBlank()) ? cleanedOriginalName : title.trim());
            document.setDescription(description == null ? "" : description.trim());
            document.setOriginalFileName(cleanedOriginalName);
            document.setStoredFileName(storedFileName);
            document.setFilePath(path);
            document.setFileSizeBytes(file.getSize());
            document.setMimeType(resolveMimeType(file, extension));
            document.setUploadedBy(requesterUserId);
            document.setActive(true);
            return hrDocumentRepo.save(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public HRDocument updateDocument(Long requesterUserId, Long id, Long employeeId, String documentType, String title,
            String description, MultipartFile file) {
        User requester = getUserOrThrow(requesterUserId);
        validateManageAccess(requester);

        Optional<HRDocument> optional = hrDocumentRepo.findById(id);
        if (optional.isEmpty() || !optional.get().isActive()) {
            return null;
        }

        HRDocument existing = optional.get();

        if (employeeId != null) {
            validateEmployeeExists(employeeId);
            existing.setEmployeeId(employeeId);
        }

        if (documentType != null && !documentType.isBlank()) {
            existing.setDocumentType(parseDocumentType(documentType));
        }

        if (title != null && !title.isBlank()) {
            existing.setTitle(title.trim());
        }

        if (description != null) {
            existing.setDescription(description.trim());
        }

        if (file != null && !file.isEmpty()) {
            validateFile(file);
            String cleanedOriginalName = sanitizeFileName(file.getOriginalFilename());
            String extension = getFileExtension(cleanedOriginalName);
            String storedFileName = System.currentTimeMillis() + "_" + cleanedOriginalName.replace(" ", "_");

            try {
                String path = fileUploadUtil.saveFile(storedFileName, file);
                existing.setOriginalFileName(cleanedOriginalName);
                existing.setStoredFileName(storedFileName);
                existing.setFilePath(path);
                existing.setFileSizeBytes(file.getSize());
                existing.setMimeType(resolveMimeType(file, extension));
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }

        return hrDocumentRepo.save(existing);
    }

    @Override
    public List<HRDocument> getAllDocuments(Long requesterUserId) {
        User requester = getUserOrThrow(requesterUserId);
        validateViewAccess(requester);

        if (canManage(requester)) {
            return hrDocumentRepo.findByActiveTrueOrderByCreatedAtDesc();
        }

        return hrDocumentRepo.findByEmployeeIdAndActiveTrueOrderByCreatedAtDesc(requester.getId());
    }

    @Override
    public HRDocument getDocumentById(Long requesterUserId, Long id) {
        User requester = getUserOrThrow(requesterUserId);
        validateViewAccess(requester);

        Optional<HRDocument> optional = hrDocumentRepo.findById(id);
        if (optional.isEmpty() || !optional.get().isActive()) {
            return null;
        }

        HRDocument document = optional.get();
        validateReadOwnership(requester, document.getEmployeeId());
        return document;
    }

    @Override
    public List<HRDocument> getDocumentsByEmployee(Long requesterUserId, Long employeeId) {
        User requester = getUserOrThrow(requesterUserId);
        validateViewAccess(requester);
        validateReadOwnership(requester, employeeId);

        return hrDocumentRepo.findByEmployeeIdAndActiveTrueOrderByCreatedAtDesc(employeeId);
    }

    @Override
    public List<HRDocument> getMyDocuments(Long requesterUserId) {
        User requester = getUserOrThrow(requesterUserId);
        validateViewAccess(requester);
        return hrDocumentRepo.findByEmployeeIdAndActiveTrueOrderByCreatedAtDesc(requester.getId());
    }

    @Override
    public boolean softDelete(Long requesterUserId, Long id) {
        User requester = getUserOrThrow(requesterUserId);
        validateManageAccess(requester);

        Optional<HRDocument> optional = hrDocumentRepo.findById(id);
        if (optional.isEmpty() || !optional.get().isActive()) {
            return false;
        }

        HRDocument document = optional.get();
        document.setActive(false);
        hrDocumentRepo.save(document);
        return true;
    }

    @Override
    public Resource getDownloadResource(Long requesterUserId, Long id) {
        HRDocument document = getDocumentById(requesterUserId, id);
        if (document == null) {
            return null;
        }

        try {
            Path filePath = Paths.get(uploadDir).resolve(document.getStoredFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return null;
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to load file", e);
        }
    }

    @Override
    public String getDownloadFileName(Long requesterUserId, Long id) {
        HRDocument document = getDocumentById(requesterUserId, id);
        return document == null ? null : document.getOriginalFileName();
    }

    private void validateEmployeeExists(Long employeeId) {
        if (employeeId == null || userRepo.findById(employeeId).isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }
    }

    private HRDocumentType parseDocumentType(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Document type is required");
        }

        try {
            return HRDocumentType.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid document type");
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new IllegalArgumentException("File size exceeds 10MB limit");
        }

        String fileName = sanitizeFileName(file.getOriginalFilename());
        String extension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("Unsupported file type");
        }
    }

    private String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Invalid file name");
        }
        return Paths.get(fileName).getFileName().toString();
    }

    private String getFileExtension(String fileName) {
        int idx = fileName.lastIndexOf('.');
        if (idx == -1 || idx == fileName.length() - 1) {
            throw new IllegalArgumentException("File extension is required");
        }
        return fileName.substring(idx + 1).toLowerCase(Locale.ROOT);
    }

    private String resolveMimeType(MultipartFile file, String extension) {
        String contentType = file.getContentType();
        if (contentType != null && !contentType.isBlank()) {
            return contentType;
        }

        if ("pdf".equals(extension)) {
            return "application/pdf";
        }

        if ("doc".equals(extension)) {
            return "application/msword";
        }

        if ("docx".equals(extension)) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }

        return "application/octet-stream";
    }

    private User getUserOrThrow(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Requester user id is required");
        }

        return userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
    }

    private void validateManageAccess(User user) {
        if (!canManage(user)) {
            throw new SecurityException("Manage permission denied");
        }
    }

    private void validateViewAccess(User user) {
        if (!(canManage(user) || hasPermission(user, "hr_docs.view"))) {
            throw new SecurityException("View permission denied");
        }
    }

    private void validateReadOwnership(User requester, Long employeeId) {
        if (canManage(requester)) {
            return;
        }

        if (!requester.getId().equals(employeeId)) {
            throw new SecurityException("You can only access your own documents");
        }
    }

    private boolean canManage(User user) {
        return isAdminRole(user) || hasPermission(user, "hr_docs.manage");
    }

    private boolean isAdminRole(User user) {
        if (user.getRoles() == null) {
            return false;
        }

        for (Role role : user.getRoles()) {
            if (role == null || role.getRole() == null) {
                continue;
            }

            String roleName = role.getRole().toLowerCase(Locale.ROOT);
            if ("admin".equals(roleName) || "super admin".equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPermission(User user, String permissionName) {
        if (user.getRoles() == null) {
            return false;
        }

        for (Role role : user.getRoles()) {
            if (role == null || role.getPermissions() == null) {
                continue;
            }

            for (Permission permission : role.getPermissions()) {
                if (permission != null && permissionName.equals(permission.getName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
