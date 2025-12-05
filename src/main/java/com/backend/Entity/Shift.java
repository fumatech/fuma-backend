package com.backend.Entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalTime;
import java.util.List;

@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long shiftType;

    private LocalTime startTime;

    private LocalTime endTime;
    
    private Long autoClockOut;
    
    private LocalTime autoClockOutTime;


    @ElementCollection
    private List<String> holiday;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getShiftType() {
        return shiftType;
    }

    public void setShiftType(Long shiftType) {
        this.shiftType = shiftType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

	public Long getAutoClockOut() {
		return autoClockOut;
	}

	public void setAutoClockOut(Long autoClockOut) {
		this.autoClockOut = autoClockOut;
	}

	public LocalTime getAutoClockOutTime() {
		return autoClockOutTime;
	}

	public void setAutoClockOutTime(LocalTime autoClockOutTime) {
		this.autoClockOutTime = autoClockOutTime;
	}

	public List<String> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<String> holiday) {
        this.holiday = holiday;
    }
}
