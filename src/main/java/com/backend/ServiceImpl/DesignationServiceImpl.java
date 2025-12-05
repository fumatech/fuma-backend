package com.backend.ServiceImpl;

import com.backend.Entity.Designation;
import com.backend.Repository.DesignationRepo;
import com.backend.Service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationRepo designationRepo;

    @Override
    public Designation saveDesignation(Designation designation) {
        return designationRepo.save(designation);
    }

    @Override
    public List<Designation> getAllDesignations() {
        return designationRepo.findAll();
    }

    @Override
    public Designation updateDesignation(Long id, Designation designation) {
        Optional<Designation> existingDesignation = designationRepo.findById(id);
        if (!existingDesignation.isPresent()) {
            throw new IllegalArgumentException("Designation with ID " + id + " does not exist.");
        }

        designation.setId(id);
        return designationRepo.save(designation);
    }

    @Override
    public Designation getDesignationById(Long id) {
        return designationRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Designation with ID " + id + " not found"));
    }

    @Override
    public void deleteDesignationById(Long id) {
        designationRepo.deleteById(id);
    }
}
