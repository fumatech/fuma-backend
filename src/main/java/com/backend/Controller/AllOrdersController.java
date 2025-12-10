package com.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.AllOrdersResponse;
import com.backend.Repository.PurchaseDIOrderRepo;
import com.backend.Repository.PurchasePoOrderRepo;
import com.backend.Repository.SaleDIOrderRepo;
import com.backend.Repository.SaleSoOrderRepo;
import com.backend.Repository.TransactionRepo;

@RestController
@RequestMapping("/allorders")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class AllOrdersController {

	@Autowired
	private PurchasePoOrderRepo purchasePoOrderRepo;

	@Autowired
	private PurchaseDIOrderRepo purchaseDIOrderRepo;

	@Autowired
	private SaleSoOrderRepo saleSoOrderRepo;

	@Autowired
	private SaleDIOrderRepo saleDIOrderRepo;

	@Autowired
	private TransactionRepo transactionRepo;

	// ✅ ONE API — ALL ORDERS
	@GetMapping("/getall")
	public AllOrdersResponse getAllOrders() {

		AllOrdersResponse response = new AllOrdersResponse();

		response.setPurchasePoOrders(purchasePoOrderRepo.findAll());
		response.setPurchaseDiOrders(purchaseDIOrderRepo.findAll());
		response.setSaleSoOrders(saleSoOrderRepo.findAll());
		response.setSaleDiOrders(saleDIOrderRepo.findAll());
		response.setTransaction(transactionRepo.findAll());
		return response;
	}
}
