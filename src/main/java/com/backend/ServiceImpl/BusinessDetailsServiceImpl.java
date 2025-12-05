package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.BusinessDetails;
import com.backend.Repository.BusinessDetailsRepo;
import com.backend.Service.BusinessDetailsService;

@Service
public class BusinessDetailsServiceImpl implements BusinessDetailsService {

    @Autowired
    private BusinessDetailsRepo businessDetailsRepo;

    @Override
    public BusinessDetails saveBusinessDetails(BusinessDetails businessDetails) {
        return businessDetailsRepo.save(businessDetails);
    }

    @Override
    public List<BusinessDetails> getAllBusinessDetails() {
        return businessDetailsRepo.findAll();
    }

    @Override
    public BusinessDetails updateBusinessDetails(Long id, BusinessDetails updatedBusinessDetails) {
        Optional<BusinessDetails> existingBusinessDetails = businessDetailsRepo.findById(id);
        if (existingBusinessDetails.isPresent()) {
            BusinessDetails businessDetails = existingBusinessDetails.get();

            businessDetails.setName(updatedBusinessDetails.getName());
            businessDetails.setAddress(updatedBusinessDetails.getAddress());
            businessDetails.setEmail(updatedBusinessDetails.getEmail());
            businessDetails.setPhoneNumber(updatedBusinessDetails.getPhoneNumber());
            businessDetails.setWebsite(updatedBusinessDetails.getWebsite());
            businessDetails.setLogo(updatedBusinessDetails.getLogo());
            businessDetails.setTaxOrGstNumber(updatedBusinessDetails.getTaxOrGstNumber());
            businessDetails.setShopActNumber(updatedBusinessDetails.getShopActNumber());
            businessDetails.setCinNumber(updatedBusinessDetails.getCinNumber());
            businessDetails.setPanNumber(updatedBusinessDetails.getPanNumber());

            return businessDetailsRepo.save(businessDetails);
        }
        return null; // Or throw a custom exception if preferred
    }


    @Override
    public BusinessDetails getBusinessDetailsById(Long id) {
        return businessDetailsRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteBusinessDetailsById(Long id) {
        businessDetailsRepo.deleteById(id);
    }
}
