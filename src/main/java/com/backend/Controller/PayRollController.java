package com.backend.Controller;

import com.backend.Entity.PayRoll;
import com.backend.Service.PayRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payroll")
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
public class PayRollController {

    @Autowired
    private PayRollService payRollService;

    @PostMapping("/add")
    public ResponseEntity<PayRoll> savePayRoll(@RequestBody PayRoll payRoll) {
        PayRoll savedPayRoll = payRollService.savePayRoll(payRoll);
        return new ResponseEntity<>(savedPayRoll, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PayRoll>> getAllPayRolls() {
        List<PayRoll> payRolls = payRollService.getAllPayRolls();
        return new ResponseEntity<>(payRolls, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PayRoll> getPayRollById(@PathVariable Long id) {
        PayRoll payRoll = payRollService.getPayRollById(id);
        if (payRoll != null) {
            return new ResponseEntity<>(payRoll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PayRoll> updatePayRoll(@PathVariable Long id, @RequestBody PayRoll updatedPayRoll) {
        PayRoll updated = payRollService.updatePayRoll(id, updatedPayRoll);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayRoll(@PathVariable Long id) {
        payRollService.deletePayRollById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
