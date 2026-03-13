package com.backend.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.Entity.HRDocument;
import com.backend.Service.HRDocumentService;

@RestController
@RequestMapping("/hr-docs")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://fusionmastertech.com",
    "https://fusionmastertech.com",
    "http://www.fusionmastertech.com",
    "https://www.fusionmastertech.com"
}, allowCredentials = "true")
public class HRDocumentController {

    @Autowired
    private HRDocumentService hrDocumentService;

    @PostMapping("/add")
    public ResponseEntity<?> addDocument(
            @RequestParam("requesterUserId") Long requesterUserId,
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("documentType") String documentType,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("file") MultipartFile file) {

        try {
            HRDocument created = hrDocumentService.addDocument(requesterUserId, employeeId, documentType, title, description,
                    file);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        } catch (RuntimeException ex) {
            return serverError(ex.getMessage());
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllDocuments(@RequestParam("requesterUserId") Long requesterUserId) {
        try {
            List<HRDocument> documents = hrDocumentService.getAllDocuments(requesterUserId);
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getDocumentById(
            @PathVariable("id") Long id,
            @RequestParam("requesterUserId") Long requesterUserId) {
        try {
            HRDocument document = hrDocumentService.getDocumentById(requesterUserId, id);
            if (document == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(document, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam("requesterUserId") Long requesterUserId) {
        try {
            List<HRDocument> documents = hrDocumentService.getDocumentsByEmployee(requesterUserId, employeeId);
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        }
    }

    @GetMapping("/my-docs/{userId}")
    public ResponseEntity<?> getMyDocs(
            @PathVariable("userId") Long userId,
            @RequestParam("requesterUserId") Long requesterUserId) {
        try {
            if (!userId.equals(requesterUserId)) {
                return forbidden("You can only access your own documents");
            }

            List<HRDocument> documents = hrDocumentService.getMyDocuments(requesterUserId);
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDocument(
            @PathVariable("id") Long id,
            @RequestParam("requesterUserId") Long requesterUserId,
            @RequestParam(value = "employeeId", required = false) Long employeeId,
            @RequestParam(value = "documentType", required = false) String documentType,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            HRDocument updated = hrDocumentService.updateDocument(requesterUserId, id, employeeId, documentType, title,
                    description, file);
            if (updated == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        } catch (RuntimeException ex) {
            return serverError(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDocument(
            @PathVariable("id") Long id,
            @RequestParam("requesterUserId") Long requesterUserId) {
        try {
            boolean deleted = hrDocumentService.softDelete(requesterUserId, id);
            if (!deleted) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadDocument(
            @PathVariable("id") Long id,
            @RequestParam("requesterUserId") Long requesterUserId) {

        try {
            Resource resource = hrDocumentService.getDownloadResource(requesterUserId, id);
            if (resource == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String originalName = hrDocumentService.getDownloadFileName(requesterUserId, id);
            String safeName = originalName == null ? resource.getFilename() : originalName;

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + safeName + "\"")
                    .body(resource);
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (SecurityException ex) {
            return forbidden(ex.getMessage());
        } catch (RuntimeException ex) {
            return serverError(ex.getMessage());
        }
    }

    private ResponseEntity<Map<String, String>> badRequest(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message == null ? "Invalid request" : message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, String>> forbidden(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message == null ? "Access denied" : message);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Map<String, String>> serverError(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message == null ? "Internal server error" : message);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
