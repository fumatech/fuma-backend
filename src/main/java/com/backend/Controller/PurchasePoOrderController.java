package com.backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.Entity.PurchasePoOrder;
import com.backend.Service.PurchasePoOrderService;

@RestController
@RequestMapping("/purchase-po-order")
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
public class PurchasePoOrderController {    

    @Autowired
    private PurchasePoOrderService purchasePoOrderService;

    @PostMapping("/save")
    public ResponseEntity<PurchasePoOrder> createPurchase(@RequestBody PurchasePoOrder purchasePoOrder) {
        System.out.println("Received Request: " + purchasePoOrder);
        PurchasePoOrder savedOrderPurchase = purchasePoOrderService.savePurchasePoOrder(purchasePoOrder);
        return new ResponseEntity<>(savedOrderPurchase, HttpStatus.CREATED);
    }


    @GetMapping("/getall")
    public ResponseEntity<List<PurchasePoOrder>> getAllPurchases() {
        List<PurchasePoOrder> orders = purchasePoOrderService.getAllPurchasePoOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<PurchasePoOrder>> getPurchaseOrderById(@PathVariable Long id) {
        Optional<PurchasePoOrder> orders = purchasePoOrderService.getPurchasePoOrderById(id);
        return orders.isPresent() ? new ResponseEntity<>(orders, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<PurchasePoOrder> updatePurchasePoOrder(
            @PathVariable Long id, 
            @RequestBody PurchasePoOrder updatedPurchasePoOrder) {
        
        PurchasePoOrder updatedOrder = purchasePoOrderService.updatePurchasePoOrder(id, updatedPurchasePoOrder);
        
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);  // Return updated PO Order
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Handle not found case
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        purchasePoOrderService.deletePurchasePoOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/getAllOrderIds")
    public ResponseEntity<List<String>> getAllOrderIds() {
        List<String> orderIds = purchasePoOrderService.getAllOrderIds();
        return new ResponseEntity<>(orderIds, HttpStatus.OK);
    }

    
}
