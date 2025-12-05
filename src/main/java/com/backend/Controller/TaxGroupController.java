package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.TaxGroup;
import com.backend.Entity.TaxRate;
import com.backend.Service.TaxGroupService;
import com.backend.Service.TaxRateService;

@RestController
@RequestMapping("/taxGroup")
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
public class TaxGroupController {

    @Autowired
    private TaxGroupService taxGroupService;

    @Autowired
    private TaxRateService taxRateService;

    @GetMapping("/getall")
    public List<TaxGroup> getAllTaxGroups() {
        return taxGroupService.getAllTaxGroups();
    }

    @PostMapping("/create")
    public TaxGroup createTaxGroup(@RequestBody TaxGroup taxGroup) {
        // Fetch tax rates based on the IDs sent in the request body
        List<TaxRate> taxRates = taxRateService.getTaxRatesByIds(taxGroup.getTaxRatesIds()); // getTaxRatesIds should be a list of IDs
        taxGroup.setTaxRates(taxRates); // Set the fetched tax rates into the taxGroup
        return taxGroupService.createTaxGroup(taxGroup);
    }

    @PutMapping("/update/{id}")
    public TaxGroup updateTaxGroup(@PathVariable Long id, @RequestBody TaxGroup taxGroup) {
        // Fetch tax rates based on the IDs sent in the request body
        List<TaxRate> taxRates = taxRateService.getTaxRatesByIds(taxGroup.getTaxRatesIds()); // getTaxRatesIds should be a list of IDs
        taxGroup.setTaxRates(taxRates); // Set the fetched tax rates into the taxGroup
        return taxGroupService.updateTaxGroup(id, taxGroup);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTaxGroup(@PathVariable Long id) {
        taxGroupService.deleteTaxGroup(id);
    }
}
