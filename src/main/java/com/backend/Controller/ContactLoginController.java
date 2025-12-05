package com.backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.ContactLogin;
import com.backend.Service.ContactLoginService;

@RestController
@RequestMapping("/contact-login")
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
public class ContactLoginController {

    @Autowired
    private ContactLoginService contactLoginService;

    @PostMapping("/save")
    public ContactLogin saveContact(@RequestBody ContactLogin contactLogin) {
        return contactLoginService.saveContact(contactLogin);
    }

    @GetMapping("/get/{id}")
    public ContactLogin getContactById(@PathVariable Long id) {
        return contactLoginService.getContactById(id);
    }

    @GetMapping("/getall")
    public List<ContactLogin> getAllContacts() {
        return contactLoginService.getAllContacts();
    }

    @PutMapping("/update/{id}")
    public ContactLogin updateContact(@PathVariable Long id, @RequestBody ContactLogin contactLogin) {
        return contactLoginService.updateContact(id, contactLogin);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactLoginService.deleteContact(id);
    }
}
