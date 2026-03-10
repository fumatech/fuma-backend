package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.FaceEncoding;
import com.backend.Repository.FaceEncodingRepo;
import com.backend.Service.FaceEncodingService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FaceEncodingServiceImpl implements FaceEncodingService {

    @Autowired
    private FaceEncodingRepo faceEncodingRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public FaceEncoding registerFace(FaceEncoding faceEncoding) {
        // If the employee already has a face encoding, update it
        FaceEncoding existing = faceEncodingRepo.findByEmployeeId(faceEncoding.getEmployeeId()).orElse(null);
        if (existing != null) {
            existing.setFaceDescriptor(faceEncoding.getFaceDescriptor());
            existing.setEmployeeName(faceEncoding.getEmployeeName());
            return faceEncodingRepo.save(existing);
        }
        return faceEncodingRepo.save(faceEncoding);
    }

    @Override
    public FaceEncoding updateFace(Long employeeId, String faceDescriptor) {
        FaceEncoding existing = faceEncodingRepo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Face encoding not found for employee: " + employeeId));
        existing.setFaceDescriptor(faceDescriptor);
        return faceEncodingRepo.save(existing);
    }

    @Override
    public FaceEncoding getByEmployeeId(Long employeeId) {
        return faceEncodingRepo.findByEmployeeId(employeeId).orElse(null);
    }

    @Override
    public List<FaceEncoding> getAllFaceEncodings() {
        return faceEncodingRepo.findAll();
    }

    @Override
    public void deleteFaceEncoding(Long id) {
        faceEncodingRepo.deleteById(id);
    }

    @Override
    public Long matchFace(String incomingDescriptorJson) {
        try {
            List<Float> incomingDescriptor = objectMapper.readValue(incomingDescriptorJson,
                    new TypeReference<List<Float>>() {
            });

            List<FaceEncoding> allFaces = faceEncodingRepo.findAll();
            double bestDistance = Double.MAX_VALUE;
            Long bestEmployeeId = null;

            for (FaceEncoding face : allFaces) {
                List<Float> storedDescriptor = objectMapper.readValue(face.getFaceDescriptor(),
                        new TypeReference<List<Float>>() {
                });

                double distance = euclideanDistance(incomingDescriptor, storedDescriptor);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestEmployeeId = face.getEmployeeId();
                }
            }

            // Threshold: 0.6 is standard for face-api.js (lower = stricter)
            if (bestDistance < 0.6 && bestEmployeeId != null) {
                return bestEmployeeId;
            }

            return null; // No match found

        } catch (Exception e) {
            throw new RuntimeException("Error matching face descriptor", e);
        }
    }

    private double euclideanDistance(List<Float> a, List<Float> b) {
        if (a.size() != b.size()) {
            throw new RuntimeException("Descriptor size mismatch");
        }
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            double diff = a.get(i) - b.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
