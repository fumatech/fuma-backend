package com.backend.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.backend.DTO.QuotationHistoryDTO;
import com.backend.DTO.QuotationListDTO;
import com.backend.DTO.QuotationRequestDTO;
import com.backend.DTO.QuotationResponseDTO;
import com.backend.DTO.QuotationStatusUpdateDTO;

public interface QuotationService {
    QuotationResponseDTO createQuotation(QuotationRequestDTO request, String userEmail);
    QuotationResponseDTO updateQuotation(Long quotationId, QuotationRequestDTO request, String userEmail);
    QuotationResponseDTO getQuotationById(Long quotationId);
    List<QuotationListDTO> getAllQuotations(Long customerId, String status, LocalDate dateFrom, LocalDate dateTo, String search);
    QuotationResponseDTO duplicateQuotation(Long quotationId, String userEmail);
    QuotationResponseDTO updateQuotationStatus(Long quotationId, QuotationStatusUpdateDTO statusUpdate, String userEmail);
    List<QuotationHistoryDTO> getQuotationHistory(Long quotationId);
    Map<String, Object> generateQuotationDocumentData(Long quotationId);
}
