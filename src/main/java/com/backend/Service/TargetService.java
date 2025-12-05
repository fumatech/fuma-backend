package com.backend.Service;

import com.backend.Entity.Target;

import java.util.List;

public interface TargetService {

    Target saveTarget(Target target);

    List<Target> getAllTargets();

    Target updateTarget(Long id, Target updatedTarget);

    Target getTargetById(Long id);

    boolean deleteTargetById(Long id);
}
