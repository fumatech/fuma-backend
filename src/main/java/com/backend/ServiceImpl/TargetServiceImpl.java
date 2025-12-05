package com.backend.ServiceImpl;

import com.backend.Entity.Target;
import com.backend.Repository.TargetRepo;
import com.backend.Service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TargetServiceImpl implements TargetService {

    private final TargetRepo targetRepo;

    @Autowired
    public TargetServiceImpl(TargetRepo targetRepo) {
        this.targetRepo = targetRepo;
    }

    @Override
    public Target saveTarget(Target target) {
        return targetRepo.save(target);
    }

    @Override
    public List<Target> getAllTargets() {
        return targetRepo.findAll();
    }

    @Override
    public Target updateTarget(Long id, Target updatedTarget) {
        Optional<Target> targetOptional = targetRepo.findById(id);
        if (targetOptional.isPresent()) {
            Target existingTarget = targetOptional.get();
            existingTarget.setEmployee(updatedTarget.getEmployee());
            existingTarget.setTotalAmountFrom(updatedTarget.getTotalAmountFrom());
            existingTarget.setTotalAmountTo(updatedTarget.getTotalAmountTo());
            existingTarget.setCommisionPercent(updatedTarget.getCommisionPercent());
            return targetRepo.save(existingTarget);
        }
        return null;
    }

    @Override
    public Target getTargetById(Long id) {
        return targetRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteTargetById(Long id) {
        Optional<Target> targetOptional = targetRepo.findById(id);
        if (targetOptional.isPresent()) {
            targetRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
