package com.backend.Service;

import java.util.List;

import com.backend.Entity.BusinessDetails;

public interface BusinessDetailsService {
	
	BusinessDetails saveBusinessDetails(BusinessDetails businessDetails);

	List<BusinessDetails> getAllBusinessDetails();

	BusinessDetails updateBusinessDetails(Long id, BusinessDetails updatedBusinessDetails);

	BusinessDetails getBusinessDetailsById(Long id);

	void deleteBusinessDetailsById(Long id);


}
