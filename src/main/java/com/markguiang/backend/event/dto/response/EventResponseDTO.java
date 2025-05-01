package com.markguiang.backend.event.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.event.enum_.EventStatus;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class EventResponseDTO {
    private Long eventId;
    private String name;
    private Calendar dateTime;
    private String location;
    private Boolean hasMultipleLocation;
    private Map<LocalDate, String> dateLocationMap;
    private String imgURL;
    private EventStatus eventStatus;
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

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
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

    public Map<LocalDate, String> getDateLocationMap() {
        return dateLocationMap;
    }

    public void setDateLocationMap(Map<LocalDate, String> dateLocationMap) {
        this.dateLocationMap = dateLocationMap;
    }

    public List<ScheduleResponseDTO> getScheduleResponseDTOList() {
        return scheduleResponseDTOList;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
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