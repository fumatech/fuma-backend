package com.backend.Service;

import com.backend.Entity.FollowUps;

import java.util.List;

public interface FollowUpsService {
    FollowUps saveFollowUp(FollowUps followUps);
    FollowUps getFollowUpById(Long id);
    List<FollowUps> getAllFollowUps();
    FollowUps updateFollowUp(Long id, FollowUps followUps);
    void deleteFollowUp(Long id);
}
