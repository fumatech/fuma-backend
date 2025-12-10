package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.ClientLedgerDTO;
import com.backend.ServiceImpl.ClientLedgerService;

@RestController
@RequestMapping("/client-ledger")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class ClientLedgerController {

	@Autowired
	private ClientLedgerService ledgerService;

	@GetMapping("/getall")
	public List<ClientLedgerDTO> getClientLedger() {
		return ledgerService.getClientLedger();
	}
}