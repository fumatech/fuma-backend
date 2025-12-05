package com.backend.Service;

import java.util.List;
import com.backend.Entity.Holiday;

public interface HolidayService {

    Holiday saveHoliday(Holiday holiday);

    List<Holiday> getAllHolidays();

    Holiday updateHoliday(Long id, Holiday updatedHoliday);

    Holiday getHolidayById(Long id);

    void deleteHolidayById(Long id);
}
