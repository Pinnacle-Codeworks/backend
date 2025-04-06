package com.markguiang.backend.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.markguiang.backend.base.BaseEntity;
import jakarta.persistence.*;

import java.util.Calendar;

@Entity
public class Schedule implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    // foreignKeys
    private Long eventId;

    // fields
    private Calendar startDate;
    private Calendar endDate;
    private String location;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public void clearIds() {
        scheduleId = null;
        eventId = null;
    }
}
