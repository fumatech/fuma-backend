package com.backend.Controller;

import com.backend.Entity.FollowUps;
import com.backend.Service.FollowUpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow-ups")
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
public class FollowUpsController {

    @Autowired
    private FollowUpsService followUpsService;

    @PostMapping("/save")
    public FollowUps saveFollowUp(@RequestBody FollowUps followUps) {
        return followUpsService.saveFollowUp(followUps);
    }

    @GetMapping("/get/{id}")
    public FollowUps getFollowUpById(@PathVariable Long id) {
        return followUpsService.getFollowUpById(id);
    }

    @GetMapping("/getall")
    public List<FollowUps> getAllFollowUps() {
        return followUpsService.getAllFollowUps();
    }

    @PutMapping("/update/{id}")
    public FollowUps updateFollowUp(@PathVariable Long id, @RequestBody FollowUps followUps) {
        return followUpsService.updateFollowUp(id, followUps);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFollowUp(@PathVariable Long id) {
        followUpsService.deleteFollowUp(id);
    }
}
