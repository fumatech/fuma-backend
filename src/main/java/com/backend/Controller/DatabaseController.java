package com.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.Entity.DatabaseRequest;
import com.backend.Entity.Response;
import com.backend.ServiceImpl.DatabaseService;

@RestController
@RequestMapping("/create")
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
public class DatabaseController {

    private final DatabaseService databaseService;

    @Autowired
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/database")
    public Response createDatabase(@RequestBody DatabaseRequest request) {
        try {
            boolean isCreated = databaseService.createDatabase(request);
            if (isCreated) {
                return new Response("Database created successfully.");
            } else {
                return new Response("Failed to create database.");
            }
        } catch (Exception e) {
            return new Response("Error: " + e.getMessage());
        }
    }
}
