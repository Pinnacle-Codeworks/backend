package com.markguiang.backend.event.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.event.dto.event.EventFieldsDTO;
import com.markguiang.backend.event.dto.schedule.ScheduleFieldsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateEventDTO {
    @JsonUnwrapped
    @Valid
    @NotNull
    private EventFieldsDTO eventFieldsDTO;
    @JsonProperty("scheduleList")
    @Valid
    @NotNull
    private List<ScheduleFieldsDTO> scheduleFieldsDTOList;

    public EventFieldsDTO getEventFieldsDTO() {
        return eventFieldsDTO;
    }

    public void setEventFieldsDTO(EventFieldsDTO eventFieldsDTO) {
        this.eventFieldsDTO = eventFieldsDTO;
    }

    public List<ScheduleFieldsDTO> getScheduleFieldsDTOList() {
        return scheduleFieldsDTOList;
    }

    public void setScheduleFieldsDTOList(List<ScheduleFieldsDTO> scheduleFieldsDTOList) {
        this.scheduleFieldsDTOList = scheduleFieldsDTOList;
    }
}