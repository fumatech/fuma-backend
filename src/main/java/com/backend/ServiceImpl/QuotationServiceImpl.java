package com.backend.ServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.DTO.QuotationHistoryDTO;
import com.backend.DTO.QuotationItemRequestDTO;
import com.backend.DTO.QuotationItemResponseDTO;
import com.backend.DTO.QuotationListDTO;
import com.backend.DTO.QuotationRequestDTO;
import com.backend.DTO.QuotationResponseDTO;
import com.backend.DTO.QuotationStatusUpdateDTO;
import com.backend.Entity.DiscountType;
import com.backend.Entity.Quotation;
import com.backend.Entity.QuotationItem;
import com.backend.Entity.QuotationStatus;
import com.backend.Entity.QuotationStatusHistory;
import com.backend.Repository.QuotationItemRepo;
import com.backend.Repository.QuotationRepo;
import com.backend.Repository.QuotationStatusHistoryRepo;
import com.backend.Service.QuotationService;

@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationRepo quotationRepo;

    @Autowired
    private QuotationItemRepo quotationItemRepo;

    @Autowired
    private QuotationStatusHistoryRepo quotationStatusHistoryRepo;

    @Override
    @Transactional
    public QuotationResponseDTO createQuotation(QuotationRequestDTO request, String userEmail) {
        validateRequest(request);

        Quotation quotation = new Quotation();
        applyHeader(request, quotation);
        quotation.setStatus(QuotationStatus.DRAFT);
        quotation.setCreatedBy(safeUser(userEmail));
        quotation.setUpdatedBy(safeUser(userEmail));
        quotation.setQuotationNo(generateQuotationNo());

        quotation = quotationRepo.save(quotation);
        List<QuotationItem> items = buildAndCalculateItems(request.getItems(), quotation);
        quotationItemRepo.saveAll(items);
        updateHeaderTotals(quotation, items);
        quotation = quotationRepo.save(quotation);

        addStatusHistory(quotation, null, QuotationStatus.DRAFT, "Created quotation", safeUser(userEmail));
        return toResponse(quotation, items);
    }

    @Override
    @Transactional
    public QuotationResponseDTO updateQuotation(Long quotationId, QuotationRequestDTO request, String userEmail) {
        validateRequest(request);
        Quotation quotation = quotationRepo.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("Quotation not found"));

        if (QuotationStatus.APPROVED.equals(quotation.getStatus())) {
            throw new IllegalStateException("Approved quotation cannot be edited directly");
        }

        applyHeader(request, quotation);
        quotation.setUpdatedBy(safeUser(userEmail));
        quotation = quotationRepo.save(quotation);

        quotationItemRepo.deleteByQuotation_Id(quotationId);
        List<QuotationItem> items = buildAndCalculateItems(request.getItems(), quotation);
        quotationItemRepo.saveAll(items);
        updateHeaderTotals(quotation, items);
        quotation = quotationRepo.save(quotation);

        return toResponse(quotation, items);
    }

    @Override
    public QuotationResponseDTO getQuotationById(Long quotationId) {
        Quotation quotation = quotationRepo.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("Quotation not found"));
        List<QuotationItem> items = quotationItemRepo.findByQuotation_Id(quotationId);
        return toResponse(quotation, items);
    }

    @Override
    public List<QuotationListDTO> getAllQuotations(Long customerId, String status, LocalDate dateFrom, LocalDate dateTo,
            String search) {
        return quotationRepo.findAll()
                .stream()
                .filter(q -> customerId == null || Objects.equals(q.getCustomerId(), customerId))
                .filter(q -> status == null || status.isBlank()
                        || q.getStatus().name().equalsIgnoreCase(status.trim()))
                .filter(q -> dateFrom == null || (q.getIssueDate() != null && !q.getIssueDate().isBefore(dateFrom)))
                .filter(q -> dateTo == null || (q.getIssueDate() != null && !q.getIssueDate().isAfter(dateTo)))
                .filter(q -> {
                    if (search == null || search.isBlank()) {
                        return true;
                    }
                    String token = search.trim().toLowerCase();
                    return (q.getQuotationNo() != null && q.getQuotationNo().toLowerCase().contains(token))
                            || String.valueOf(q.getCustomerId()).contains(token)
                            || (q.getSubject() != null && q.getSubject().toLowerCase().contains(token));
                })
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .map(this::toListDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuotationResponseDTO duplicateQuotation(Long quotationId, String userEmail) {
        Quotation original = quotationRepo.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("Quotation not found"));
        List<QuotationItem> originalItems = quotationItemRepo.findByQuotation_Id(quotationId);

        Quotation duplicate = new Quotation();
        duplicate.setQuotationNo(generateQuotationNo());
        duplicate.setCustomerId(original.getCustomerId());
        duplicate.setIssueDate(LocalDate.now());
        duplicate.setValidTill(original.getValidTill());
        duplicate.setCurrency(original.getCurrency());
        duplicate.setReference(original.getReference());
        duplicate.setSubject(original.getSubject());
        duplicate.setNotes(original.getNotes());
        duplicate.setTerms(original.getTerms());
        duplicate.setInternalRemarks(original.getInternalRemarks());
        duplicate.setStatus(QuotationStatus.DRAFT);
        duplicate.setCreatedBy(safeUser(userEmail));
        duplicate.setUpdatedBy(safeUser(userEmail));
        duplicate = quotationRepo.save(duplicate);

        List<QuotationItem> clonedItems = new ArrayList<>();
        for (QuotationItem item : originalItems) {
            QuotationItem clone = new QuotationItem();
            clone.setQuotation(duplicate);
            clone.setItemType(item.getItemType());
            clone.setItemId(item.getItemId());
            clone.setDescription(item.getDescription());
            clone.setQuantity(item.getQuantity());
            clone.setUnitPrice(item.getUnitPrice());
            clone.setDiscountType(item.getDiscountType());
            clone.setDiscountValue(item.getDiscountValue());
            clone.setTaxRate(item.getTaxRate());
            clone.setLineSubtotal(item.getLineSubtotal());
            clone.setLineDiscount(item.getLineDiscount());
            clone.setLineTax(item.getLineTax());
            clone.setLineTotal(item.getLineTotal());
            clonedItems.add(clone);
        }

        quotationItemRepo.saveAll(clonedItems);
        updateHeaderTotals(duplicate, clonedItems);
        duplicate = quotationRepo.save(duplicate);
        addStatusHistory(duplicate, null, QuotationStatus.DRAFT, "Duplicated from " + original.getQuotationNo(),
                safeUser(userEmail));

        return toResponse(duplicate, clonedItems);
    }

    @Override
    @Transactional
    public QuotationResponseDTO updateQuotationStatus(Long quotationId, QuotationStatusUpdateDTO statusUpdate,
            String userEmail) {
        Quotation quotation = quotationRepo.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("Quotation not found"));
        if (statusUpdate == null || statusUpdate.getToStatus() == null) {
            throw new IllegalArgumentException("Target status is required");
        }

        validateStatusTransition(quotation.getStatus(), statusUpdate.getToStatus());
        QuotationStatus fromStatus = quotation.getStatus();
        quotation.setStatus(statusUpdate.getToStatus());
        quotation.setUpdatedBy(safeUser(userEmail));
        quotation.setUpdatedAt(LocalDateTime.now());
        quotation = quotationRepo.save(quotation);

        addStatusHistory(quotation, fromStatus, statusUpdate.getToStatus(), statusUpdate.getComment(), safeUser(userEmail));
        List<QuotationItem> items = quotationItemRepo.findByQuotation_Id(quotationId);
        return toResponse(quotation, items);
    }

    @Override
    public List<QuotationHistoryDTO> getQuotationHistory(Long quotationId) {
        return quotationStatusHistoryRepo.findByQuotation_IdOrderByChangedAtDesc(quotationId).stream().map(h -> {
            QuotationHistoryDTO dto = new QuotationHistoryDTO();
            dto.setFromStatus(h.getFromStatus());
            dto.setToStatus(h.getToStatus());
            dto.setComment(h.getComment());
            dto.setChangedBy(h.getChangedBy());
            dto.setChangedAt(h.getChangedAt());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> generateQuotationDocumentData(Long quotationId) {
        QuotationResponseDTO quotation = getQuotationById(quotationId);
        Map<String, Object> data = new HashMap<>();
        data.put("quotation", quotation);
        data.put("generatedAt", LocalDateTime.now());
        data.put("documentType", "QUOTATION");
        return data;
    }

    private void validateRequest(QuotationRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer is required");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("At least one item is required");
        }
        if (request.getIssueDate() != null && request.getValidTill() != null
                && request.getValidTill().isBefore(request.getIssueDate())) {
            throw new IllegalArgumentException("Valid till date cannot be before issue date");
        }
    }

    private void validateStatusTransition(QuotationStatus from, QuotationStatus to) {
        if (from == null || to == null || from == to) {
            return;
        }
        if (from == QuotationStatus.DRAFT && (to == QuotationStatus.SENT || to == QuotationStatus.APPROVED)) {
            return;
        }
        if (from == QuotationStatus.SENT && (to == QuotationStatus.VIEWED || to == QuotationStatus.DRAFT)) {
            return;
        }
        if (from == QuotationStatus.VIEWED && to == QuotationStatus.APPROVED) {
            return;
        }
        throw new IllegalStateException("Invalid status transition: " + from + " -> " + to);
    }

    private void applyHeader(QuotationRequestDTO request, Quotation quotation) {
        quotation.setCustomerId(request.getCustomerId());
        quotation.setIssueDate(request.getIssueDate());
        quotation.setValidTill(request.getValidTill());
        quotation.setCurrency(request.getCurrency());
        quotation.setReference(request.getReference());
        quotation.setSubject(request.getSubject());
        quotation.setNotes(request.getNotes());
        quotation.setTerms(request.getTerms());
        quotation.setInternalRemarks(request.getInternalRemarks());
    }

    private List<QuotationItem> buildAndCalculateItems(List<QuotationItemRequestDTO> items, Quotation quotation) {
        List<QuotationItem> entities = new ArrayList<>();
        for (QuotationItemRequestDTO item : items) {
            if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }
            if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Unit price cannot be negative");
            }

            QuotationItem qItem = new QuotationItem();
            qItem.setQuotation(quotation);
            qItem.setItemType(item.getItemType());
            qItem.setItemId(item.getItemId());
            qItem.setDescription(item.getDescription());
            qItem.setQuantity(item.getQuantity());
            qItem.setUnitPrice(item.getUnitPrice());
            qItem.setDiscountType(item.getDiscountType() == null ? DiscountType.NONE : item.getDiscountType());
            qItem.setDiscountValue(defaultZero(item.getDiscountValue()));
            qItem.setTaxRate(defaultZero(item.getTaxRate()));

            calculateLineTotals(qItem);
            entities.add(qItem);
        }
        return entities;
    }

    private void calculateLineTotals(QuotationItem item) {
        BigDecimal quantity = defaultZero(item.getQuantity());
        BigDecimal unitPrice = defaultZero(item.getUnitPrice());
        BigDecimal lineSubtotal = quantity.multiply(unitPrice);

        BigDecimal discountValue = defaultZero(item.getDiscountValue());
        BigDecimal lineDiscount;
        if (item.getDiscountType() == DiscountType.PERCENT) {
            lineDiscount = lineSubtotal.multiply(discountValue).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        } else if (item.getDiscountType() == DiscountType.AMOUNT) {
            lineDiscount = discountValue;
        } else {
            lineDiscount = BigDecimal.ZERO;
        }

        if (lineDiscount.compareTo(lineSubtotal) > 0) {
            throw new IllegalArgumentException("Discount cannot exceed line subtotal");
        }

        BigDecimal taxableAmount = lineSubtotal.subtract(lineDiscount);
        BigDecimal lineTax = taxableAmount.multiply(defaultZero(item.getTaxRate()))
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        BigDecimal lineTotal = taxableAmount.add(lineTax);

        item.setLineSubtotal(lineSubtotal);
        item.setLineDiscount(lineDiscount);
        item.setLineTax(lineTax);
        item.setLineTotal(lineTotal);
    }

    private void updateHeaderTotals(Quotation quotation, List<QuotationItem> items) {
        BigDecimal subtotal = items.stream().map(i -> defaultZero(i.getLineSubtotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountTotal = items.stream().map(i -> defaultZero(i.getLineDiscount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal taxTotal = items.stream().map(i -> defaultZero(i.getLineTax()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal grandTotal = items.stream().map(i -> defaultZero(i.getLineTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        quotation.setSubtotal(subtotal);
        quotation.setDiscountTotal(discountTotal);
        quotation.setTaxTotal(taxTotal);
        quotation.setGrandTotal(grandTotal);
    }

    private QuotationResponseDTO toResponse(Quotation quotation, List<QuotationItem> items) {
        QuotationResponseDTO dto = new QuotationResponseDTO();
        dto.setId(quotation.getId());
        dto.setQuotationNo(quotation.getQuotationNo());
        dto.setCustomerId(quotation.getCustomerId());
        dto.setIssueDate(quotation.getIssueDate());
        dto.setValidTill(quotation.getValidTill());
        dto.setCurrency(quotation.getCurrency());
        dto.setReference(quotation.getReference());
        dto.setSubject(quotation.getSubject());
        dto.setNotes(quotation.getNotes());
        dto.setTerms(quotation.getTerms());
        dto.setInternalRemarks(quotation.getInternalRemarks());
        dto.setStatus(quotation.getStatus());
        dto.setSubtotal(quotation.getSubtotal());
        dto.setDiscountTotal(quotation.getDiscountTotal());
        dto.setTaxTotal(quotation.getTaxTotal());
        dto.setGrandTotal(quotation.getGrandTotal());
        dto.setCreatedBy(quotation.getCreatedBy());
        dto.setUpdatedBy(quotation.getUpdatedBy());
        dto.setCreatedAt(quotation.getCreatedAt());
        dto.setUpdatedAt(quotation.getUpdatedAt());

        List<QuotationItemResponseDTO> itemDTOs = items.stream().map(item -> {
            QuotationItemResponseDTO itemDto = new QuotationItemResponseDTO();
            itemDto.setId(item.getId());
            itemDto.setItemType(item.getItemType());
            itemDto.setItemId(item.getItemId());
            itemDto.setDescription(item.getDescription());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setDiscountType(item.getDiscountType());
            itemDto.setDiscountValue(item.getDiscountValue());
            itemDto.setTaxRate(item.getTaxRate());
            itemDto.setLineSubtotal(item.getLineSubtotal());
            itemDto.setLineDiscount(item.getLineDiscount());
            itemDto.setLineTax(item.getLineTax());
            itemDto.setLineTotal(item.getLineTotal());
            return itemDto;
        }).collect(Collectors.toList());
        dto.setItems(itemDTOs);
        return dto;
    }

    private QuotationListDTO toListDTO(Quotation quotation) {
        QuotationListDTO dto = new QuotationListDTO();
        dto.setId(quotation.getId());
        dto.setQuotationNo(quotation.getQuotationNo());
        dto.setCustomerId(quotation.getCustomerId());
        dto.setIssueDate(quotation.getIssueDate());
        dto.setValidTill(quotation.getValidTill());
        dto.setStatus(quotation.getStatus());
        dto.setGrandTotal(quotation.getGrandTotal());
        dto.setUpdatedBy(quotation.getUpdatedBy());
        dto.setUpdatedAt(quotation.getUpdatedAt());
        return dto;
    }

    private void addStatusHistory(Quotation quotation, QuotationStatus fromStatus, QuotationStatus toStatus, String comment,
            String changedBy) {
        QuotationStatusHistory history = new QuotationStatusHistory();
        history.setQuotation(quotation);
        history.setFromStatus(fromStatus);
        history.setToStatus(toStatus);
        history.setComment(comment);
        history.setChangedBy(changedBy);
        quotationStatusHistoryRepo.save(history);
    }

    private String generateQuotationNo() {
        long nextId = quotationRepo.findTopByOrderByIdDesc().map(q -> q.getId() + 1).orElse(1L);
        int year = LocalDate.now().getYear();
        return String.format("QT-%d-%06d", year, nextId);
    }

    private BigDecimal defaultZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private String safeUser(String userEmail) {
        return (userEmail == null || userEmail.isBlank()) ? "system" : userEmail;
    }
}
