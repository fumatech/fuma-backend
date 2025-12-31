package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.Entity.BusinessLocation;
import com.backend.Repository.BusinessLocationRepository;
import com.backend.Service.BusinessLocationService;

@Service
public class BusinessLocationServiceImpl implements BusinessLocationService {

	private final BusinessLocationRepository repository;

	public BusinessLocationServiceImpl(BusinessLocationRepository repository) {
		this.repository = repository;
	}

	@Override
	public BusinessLocation save(BusinessLocation businessLocation) {
		return repository.save(businessLocation);
	}

	@Override
	public BusinessLocation getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("BusinessLocation not found"));
	}

	@Override
	public List<BusinessLocation> getAll() {
		return repository.findAll();
	}

	@Override
	public BusinessLocation update(Long id, BusinessLocation businessLocation) {
		BusinessLocation existing = getById(id);

		existing.setIsActive(businessLocation.getIsActive());
		existing.setName(businessLocation.getName());
		existing.setLocationId(businessLocation.getLocationId());
		existing.setLandmark(businessLocation.getLandmark());
		existing.setCity(businessLocation.getCity());
		existing.setZipCode(businessLocation.getZipCode());
		existing.setState(businessLocation.getState());
		existing.setCountry(businessLocation.getCountry());
		existing.setMobileNumber(businessLocation.getMobileNumber());
		existing.setAlternateContactNumber(businessLocation.getAlternateContactNumber());
		existing.setEmail(businessLocation.getEmail());
		existing.setWebsite(businessLocation.getWebsite());
		existing.setBusinessCategoryId(businessLocation.getBusinessCategoryId());
		existing.setInvoiceSchemeForPosId(businessLocation.getInvoiceSchemeForPosId());
		existing.setInvoiceSchemeForSaleId(businessLocation.getInvoiceSchemeForSaleId());
		existing.setInvoiceLayoutForPosId(businessLocation.getInvoiceLayoutForPosId());
		existing.setInvoiceLayoutForSaleId(businessLocation.getInvoiceLayoutForSaleId());
		existing.setDefaultSellingPriceGroupId(businessLocation.getDefaultSellingPriceGroupId());
		existing.setCustomField1(businessLocation.getCustomField1());
		existing.setCustomField2(businessLocation.getCustomField2());
		existing.setCustomField3(businessLocation.getCustomField3());
		existing.setCustomField4(businessLocation.getCustomField4());
		existing.setFeaturedProducts(businessLocation.getFeaturedProducts());
		existing.setDefaultPaymentAccount(businessLocation.getDefaultPaymentAccount());
		existing.setPrintReceiptOnInvoice(businessLocation.getPrintReceiptOnInvoice());
		existing.setPrinterId(businessLocation.getPrinterId());
		existing.setReceiptPrinterType(businessLocation.getReceiptPrinterType());

		return repository.save(existing);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
