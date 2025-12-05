package com.backend.Controller;

import com.backend.Entity.BusinessDetails;
import com.backend.Service.BusinessDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business-details")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	    },
	    allowCredentials = "true"
	)
public class BusinessDetailsController {

    @Autowired
    private BusinessDetailsService businessDetailsService;

    @PostMapping("/save")
    public BusinessDetails saveBusinessDetails(@RequestBody BusinessDetails businessDetails) {
        return businessDetailsService.saveBusinessDetails(businessDetails);
    }

    @GetMapping("/getall")
    public List<BusinessDetails> getAllBusinessDetails() {
        return businessDetailsService.getAllBusinessDetails();
    }

    @GetMapping("/get/{id}")
    public BusinessDetails getBusinessDetailsById(@PathVariable Long id) {
        return businessDetailsService.getBusinessDetailsById(id);
    }

    @PutMapping("/update/{id}")
    public BusinessDetails updateBusinessDetails(@PathVariable Long id, @RequestBody BusinessDetails updatedBusinessDetails) {
        return businessDetailsService.updateBusinessDetails(id, updatedBusinessDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBusinessDetailsById(@PathVariable Long id) {
        businessDetailsService.deleteBusinessDetailsById(id);
    }
}
