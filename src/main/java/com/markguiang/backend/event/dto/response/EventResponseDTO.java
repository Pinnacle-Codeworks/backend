package com.markguiang.backend.event.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.List;

public class EventResponseDTO {
    private Long eventId;
    private String name;
    private String description;
    private String location;
    private Boolean hasMultipleLocation;
    @JsonProperty("scheduleList")
    private List<ScheduleResponseDTO> scheduleResponseDTOList;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getHasMultipleLocation() {
        return hasMultipleLocation;
    }

    public void setHasMultipleLocation(Boolean hasMultipleLocation) {
        this.hasMultipleLocation = hasMultipleLocation;
    }

    public List<ScheduleResponseDTO> getScheduleResponseDTOList() {
        return scheduleResponseDTOList;
    }

    public void setScheduleResponseDTOList(List<ScheduleResponseDTO> scheduleResponseDTOList) {
        this.scheduleResponseDTOList = scheduleResponseDTOList;
    }

    public static class ScheduleResponseDTO {
        private Long scheduleId;
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
    }
}