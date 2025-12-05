package com.backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.SaleReturn;
import com.backend.Service.SaleReturnService;

@RestController
@RequestMapping("/sale-return")
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
public class SaleReturnController {

    @Autowired
    private SaleReturnService saleReturnService;

    // Save Sale Return
    @PostMapping("/save")
    public ResponseEntity<SaleReturn> createSaleReturn(@RequestBody SaleReturn saleReturn) {
        SaleReturn savedSaleReturn = saleReturnService.saveSaleReturn(saleReturn);
        return new ResponseEntity<>(savedSaleReturn, HttpStatus.CREATED);
    }

    // Get All Sale Returns
    @GetMapping("/getall")
    public ResponseEntity<List<SaleReturn>> getAllSaleReturns() {
        List<SaleReturn> returns = saleReturnService.getAllSaleReturns();
        return new ResponseEntity<>(returns, HttpStatus.OK);
    }

    // Get Sale Return by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<SaleReturn>> getSaleReturnById(@PathVariable Long id) {
        Optional<SaleReturn> returns = saleReturnService.getSaleReturnById(id);
        return returns.isPresent() ? new ResponseEntity<>(returns, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update Sale Return
    @PutMapping("/update/{id}")
    public ResponseEntity<SaleReturn> updateSaleReturn(@PathVariable Long id, @RequestBody SaleReturn saleReturn) {
        SaleReturn updatedSaleReturn = saleReturnService.updateSaleReturn(id, saleReturn);
        return updatedSaleReturn != null ? new ResponseEntity<>(updatedSaleReturn, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete Sale Return
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSaleReturn(@PathVariable Long id) {
        saleReturnService.deleteSaleReturn(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All Return IDs
    @GetMapping("/getAllReturnIds")
    public ResponseEntity<List<String>> getAllReturnIds() {
        List<String> returnIds = saleReturnService.getAllReturnIds();
        return new ResponseEntity<>(returnIds, HttpStatus.OK);
    }

    // Get Sale Return by PR ID
    @GetMapping("/getByPRId/{id}")
    public ResponseEntity<SaleReturn> getSaleReturnByPRId(@PathVariable String id) {
        Optional<SaleReturn> saleReturn = saleReturnService.getSaleReturnByPRId(id);
        return saleReturn.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get Sale Return IDs by Status
    @GetMapping("/getReturnIdsByStatus/{status}")
    public ResponseEntity<List<String>> getSaleReturnIdsByStatus(@PathVariable Long status) {
        List<String> saleReturnIds = saleReturnService.getSaleReturnIdsByStatus(status);
        return new ResponseEntity<>(saleReturnIds, HttpStatus.OK);
    }

    // Update Sale Return Status
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<SaleReturn> updateSaleReturnStatus(@PathVariable Long id, @RequestBody SaleReturn updatedSaleReturn) {
        Optional<SaleReturn> existingSaleReturn = saleReturnService.getSaleReturnById(id);
        if (existingSaleReturn.isPresent()) {
            SaleReturn saleReturn = existingSaleReturn.get();
            saleReturn.setStatus(updatedSaleReturn.getStatus());
            SaleReturn updatedReturn = saleReturnService.saveSaleReturn(saleReturn);
            return new ResponseEntity<>(updatedReturn, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get Pending Sale Returns
    @GetMapping("/getPendingReturns")
    public ResponseEntity<List<SaleReturn>> getPendingReturns() {
        List<SaleReturn> pendingReturns = saleReturnService.getPendingOrders();
        return new ResponseEntity<>(pendingReturns, HttpStatus.OK);
    }

    // Get Accepted Sale Returns
    @GetMapping("/getAcceptedReturns")
    public ResponseEntity<List<SaleReturn>> getAcceptedReturns() {
        List<SaleReturn> acceptedReturns = saleReturnService.getAcceptedOrders();
        return new ResponseEntity<>(acceptedReturns, HttpStatus.OK);
    }

    // Get Rejected Sale Returns
    @GetMapping("/getRejectedReturns")
    public ResponseEntity<List<SaleReturn>> getRejectedReturns() {
        List<SaleReturn> rejectedReturns = saleReturnService.getRejectedOrders();
        return new ResponseEntity<>(rejectedReturns, HttpStatus.OK);
    }

    // Get Ship Sale Returns
    @GetMapping("/getShipReturns")
    public ResponseEntity<List<SaleReturn>> getShipReturns() {
        List<SaleReturn> shipReturns = saleReturnService.getShipOrders();
        return new ResponseEntity<>(shipReturns, HttpStatus.OK);
    }

    // Get Total Shipped Items by Sale Return ID
    @GetMapping("/getTotalShippedItems/{saleReturnId}")
    public ResponseEntity<Long> getTotalShippedItems(@PathVariable String saleReturnId) {
        Long totalShippedItems = saleReturnService.getTotalShippedItems(saleReturnId);
        return ResponseEntity.ok(totalShippedItems);
    }
}
