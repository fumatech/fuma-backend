package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.ServiceImpl.CombinedPurchaseOrderService;

@RestController
@RequestMapping("/purchase-combined-orders")
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
public class CombinedPurchaseOrderController {

    @Autowired
    private CombinedPurchaseOrderService combinedPurchaseOrderService;

    // Fetch purchases for a specific vendor
    @GetMapping("/getbyvendor/{vendor}")
    public ResponseEntity<Map<String, List<Object>>> getPurchasesByVendor(@PathVariable String vendor) {
        Map<String, List<Object>> orders = combinedPurchaseOrderService.getPurchasesByVendor(vendor);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/with-tax")
    public ResponseEntity<Map<String, List<Object>>> getAllOrdersWithTax() {
        return ResponseEntity.ok(
            combinedPurchaseOrderService.getAllPurchaseOrdersWithTax()
        );
    }
}
