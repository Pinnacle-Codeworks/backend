package com.markguiang.backend.event.dto.schedule;

import jakarta.validation.constraints.NotNull;

public class ScheduleIdsDTO {
    @NotNull
    private Long scheduleId;
    @NotNull
    private Long eventId;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}