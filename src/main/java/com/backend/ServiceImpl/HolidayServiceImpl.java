package com.backend.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.Holiday;
import com.backend.Repository.HolidayRepo;
import com.backend.Service.HolidayService;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayRepo holidayRepo;

    @Override
    public Holiday saveHoliday(Holiday holiday) {
        return holidayRepo.save(holiday);
    }

    @Override
    public List<Holiday> getAllHolidays() {
        return holidayRepo.findAll();
    }

    @Override
    public Holiday updateHoliday(Long id, Holiday updatedHoliday) {
        Optional<Holiday> existingHoliday = holidayRepo.findById(id);
        if (existingHoliday.isPresent()) {
            Holiday holiday = existingHoliday.get();
            holiday.setName(updatedHoliday.getName());
            holiday.setStartDate(updatedHoliday.getStartDate());
            holiday.setEndDate(updatedHoliday.getEndDate());
            holiday.setBusinessLocation(updatedHoliday.getBusinessLocation());
            holiday.setNote(updatedHoliday.getNote());
            return holidayRepo.save(holiday);
        } else {
            return null; // or throw an exception
        }
    }

    @Override
    public Holiday getHolidayById(Long id) {
        Optional<Holiday> holiday = holidayRepo.findById(id);
        return holiday.orElse(null);  // Returns null if holiday not found
    }

    @Override
    public void deleteHolidayById(Long id) {
        holidayRepo.deleteById(id);
    }
}
