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

import com.backend.Entity.FranchisePurchaseReturn;
import com.backend.Entity.PurchaseOrder;
import com.backend.Service.FranchisePurchaseReturnService;

@RestController
@RequestMapping("/franchise-purchase-return")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class FranchisePurchaseReturnController {

	@Autowired
	private FranchisePurchaseReturnService service;

	@PostMapping("/save")
	public ResponseEntity<FranchisePurchaseReturn> save(@RequestBody FranchisePurchaseReturn pr) {
		return new ResponseEntity<>(service.saveFranchisePurchaseReturn(pr), HttpStatus.CREATED);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<FranchisePurchaseReturn>> getAll() {
		return ResponseEntity.ok(service.getAllFranchisePurchaseReturns());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<FranchisePurchaseReturn> getById(@PathVariable Long id) {
		return service.getFranchisePurchaseReturnById(id).map(ResponseEntity::ok)
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<FranchisePurchaseReturn> update(@PathVariable Long id,
			@RequestBody FranchisePurchaseReturn pr) {
		FranchisePurchaseReturn updated = service.updateFranchisePurchaseReturn(id, pr);
		return updated != null ? ResponseEntity.ok(updated) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deleteFranchisePurchaseReturn(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/getAllOrderIds")
	public ResponseEntity<List<String>> getAllReturnIds() {
		return ResponseEntity.ok(service.getAllReturnIds());
	}

	@GetMapping("/getPoDataById/{id}")
	public ResponseEntity<FranchisePurchaseReturn> getByStringId(@PathVariable String id) {
		return service.getFranchisePurchaseReturnByIdString(id).map(ResponseEntity::ok)
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/getOrderIdsByStatus/{status}")
	public ResponseEntity<List<String>> getByStatus(@PathVariable Long status) {
		return ResponseEntity.ok(service.getReturnIdsByStatus(status));
	}

	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<FranchisePurchaseReturn> updateStatus(@PathVariable Long id,
			@RequestBody PurchaseOrder updatedStatus) {
		Optional<FranchisePurchaseReturn> order = service.getFranchisePurchaseReturnById(id);
		if (order.isPresent()) {
			FranchisePurchaseReturn pr = order.get();
			pr.setStatus(updatedStatus.getStatus());
			return ResponseEntity.ok(service.saveFranchisePurchaseReturn(pr));
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getPendingReturns")
	public ResponseEntity<List<FranchisePurchaseReturn>> getPending() {
		return ResponseEntity.ok(service.getPendingReturns());
	}

	@GetMapping("/getAcceptedReturns")
	public ResponseEntity<List<FranchisePurchaseReturn>> getAccepted() {
		return ResponseEntity.ok(service.getAcceptedReturns());
	}

	@GetMapping("/getRejectedReturns")
	public ResponseEntity<List<FranchisePurchaseReturn>> getRejected() {
		return ResponseEntity.ok(service.getRejectedReturns());
	}

	@GetMapping("/getShipReturns")
	public ResponseEntity<List<FranchisePurchaseReturn>> getShipped() {
		return ResponseEntity.ok(service.getShipReturns());
	}

	@GetMapping("/getTotalShippedItems/{id}")
	public ResponseEntity<Long> getTotalShipped(@PathVariable String id) {
		return ResponseEntity.ok(service.getTotalShippedItems(id));
	}

	@PutMapping("/updatePaymentStatus/{id}")
	public ResponseEntity<FranchisePurchaseReturn> updatePaymentStatus(@PathVariable Long id,
			@RequestBody FranchisePurchaseReturn updatedData) {
		Optional<FranchisePurchaseReturn> order = service.getFranchisePurchaseReturnById(id);

		if (order.isPresent()) {
			FranchisePurchaseReturn pr = order.get();
			pr.setPaymentStatus(updatedData.getPaymentStatus());
			return ResponseEntity.ok(service.saveFranchisePurchaseReturn(pr));
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
