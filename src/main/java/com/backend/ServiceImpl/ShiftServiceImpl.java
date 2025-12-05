package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Shift;
import com.backend.Repository.ShiftRepo;
import com.backend.Service.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepo shiftRepo;

    @Override
    public Shift saveShift(Shift shift) {
        return shiftRepo.save(shift);
    }

    @Override
    public List<Shift> getAllShifts() {
        return shiftRepo.findAll();
    }

    @Override
    public Shift updateShift(Long id, Shift updatedShift) {
        Optional<Shift> existingShift = shiftRepo.findById(id);
        if (existingShift.isPresent()) {
            Shift shift = existingShift.get();
            shift.setName(updatedShift.getName());
            shift.setShiftType(updatedShift.getShiftType());
            shift.setStartTime(updatedShift.getStartTime());
            shift.setEndTime(updatedShift.getEndTime());
            shift.setAutoClockOut(updatedShift.getAutoClockOut());
            shift.setAutoClockOutTime(updatedShift.getAutoClockOutTime());
            shift.setHoliday(updatedShift.getHoliday());
            return shiftRepo.save(shift);
        } else {
            return null;  // or throw a custom exception (e.g., ShiftNotFoundException)
        }
    }

    @Override
    public Shift getShiftById(Long id) {
        Optional<Shift> shift = shiftRepo.findById(id);
        return shift.orElse(null);  // Returns null if shift is not found
    }

    @Override
    public void deleteShiftById(Long id) {
        shiftRepo.deleteById(id);
    }
}
