package com.backend.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.DTO.QuotationHistoryDTO;
import com.backend.DTO.QuotationListDTO;
import com.backend.DTO.QuotationRequestDTO;
import com.backend.DTO.QuotationResponseDTO;
import com.backend.DTO.QuotationStatusUpdateDTO;
import com.backend.Service.QuotationService;

@RestController
@RequestMapping("/quotation")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
        "http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

    @PostMapping("/save")
    public ResponseEntity<?> saveQuotation(@RequestBody QuotationRequestDTO request,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        try {
            QuotationResponseDTO response = quotationService.createQuotation(request, userEmail);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuotation(@PathVariable Long id, @RequestBody QuotationRequestDTO request,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        try {
            QuotationResponseDTO response = quotationService.updateQuotation(id, request, userEmail);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQuotation(@PathVariable Long id) {
        try {
            QuotationResponseDTO response = quotationService.getQuotationById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<QuotationListDTO>> getAllQuotations(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(quotationService.getAllQuotations(customerId, status, dateFrom, dateTo, search));
    }

    @PostMapping("/duplicate/{id}")
    public ResponseEntity<?> duplicateQuotation(@PathVariable Long id,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        try {
            QuotationResponseDTO response = quotationService.duplicateQuotation(id, userEmail);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody QuotationStatusUpdateDTO statusUpdate,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        try {
            QuotationResponseDTO response = quotationService.updateQuotationStatus(id, statusUpdate, userEmail);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<QuotationHistoryDTO>> getHistory(@PathVariable Long id) {
        return ResponseEntity.ok(quotationService.getQuotationHistory(id));
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<?> generateDocumentData(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(quotationService.generateQuotationDocumentData(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
