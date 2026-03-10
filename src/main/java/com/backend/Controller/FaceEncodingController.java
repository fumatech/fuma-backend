package com.backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.backend.Entity.FaceEncoding;
import com.backend.Service.FaceEncodingService;

@RestController
@RequestMapping("/face-encoding")
@CrossOrigin(origins = {"http://localhost:3000", "http://fusionmastertech.com",
    "https://fusionmastertech.com"}, allowCredentials = "true")
public class FaceEncodingController {

    @Autowired
    private FaceEncodingService faceEncodingService;

    // Register face
    @PostMapping("/register")
    public ResponseEntity<FaceEncoding> registerFace(@RequestBody FaceEncoding faceEncoding) {
        return ResponseEntity.ok(faceEncodingService.registerFace(faceEncoding));
    }

    // Update face
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<FaceEncoding> updateFace(@PathVariable Long employeeId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(faceEncodingService.updateFace(employeeId, body.get("faceDescriptor")));
    }

    // Get by employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Long employeeId) {
        FaceEncoding encoding = faceEncodingService.getByEmployeeId(employeeId);
        if (encoding == null) {
            return ResponseEntity.ok(Map.of("registered", false));
        }
        return ResponseEntity.ok(encoding);
    }

    // Get all stored faces
    @GetMapping("/getall")
    public List<FaceEncoding> getAllFaceEncodings() {
        return faceEncodingService.getAllFaceEncodings();
    }

    // Delete face encoding
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFaceEncoding(@PathVariable Long id) {
        faceEncodingService.deleteFaceEncoding(id);
        return ResponseEntity.ok("Face encoding deleted");
    }

    // Match face - returns matched employeeId
    @PostMapping("/match")
    public ResponseEntity<?> matchFace(@RequestBody Map<String, String> body) {
        String faceDescriptor = body.get("faceDescriptor");
        Long matchedEmployeeId = faceEncodingService.matchFace(faceDescriptor);
        if (matchedEmployeeId != null) {
            return ResponseEntity.ok(Map.of("matched", true, "employeeId", matchedEmployeeId));
        }
        return ResponseEntity.ok(Map.of("matched", false));
    }

}
