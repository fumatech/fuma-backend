package com.backend.ServiceImpl;

import com.backend.Entity.FollowUps;
import com.backend.Repository.FollowUpsRepo;
import com.backend.Service.FollowUpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowUpsServiceImpl implements FollowUpsService {

    @Autowired
    private FollowUpsRepo followUpsRepo;

    @Override
    public FollowUps saveFollowUp(FollowUps followUps) {
        return followUpsRepo.save(followUps);
    }

    @Override
    public FollowUps getFollowUpById(Long id) {
        return followUpsRepo.findById(id).orElse(null);
    }

    @Override
    public List<FollowUps> getAllFollowUps() {
        return followUpsRepo.findAll();
    }

    @Override
    public FollowUps updateFollowUp(Long id, FollowUps followUps) {
        Optional<FollowUps> existing = followUpsRepo.findById(id);
        if (existing.isPresent()) {
            FollowUps existingFollowUp = existing.get();
            existingFollowUp.setTitle(followUps.getTitle());
            existingFollowUp.setCustomer(followUps.getCustomer());
            existingFollowUp.setStatus(followUps.getStatus());
            existingFollowUp.setStartDateAndTime(followUps.getStartDateAndTime());
            existingFollowUp.setDescription(followUps.getDescription());
            existingFollowUp.setType(followUps.getType());
            existingFollowUp.setAssigned(followUps.getAssigned());
            existingFollowUp.setNotification(followUps.isNotification());
            return followUpsRepo.save(existingFollowUp);
        } else {
            return null;
        }
    }

    @Override
    public void deleteFollowUp(Long id) {
        followUpsRepo.deleteById(id);
    }
}
