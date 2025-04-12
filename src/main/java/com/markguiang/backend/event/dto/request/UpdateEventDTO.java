package com.markguiang.backend.event.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.annotation.ValidRequired;
import com.markguiang.backend.event.dto.event.EventFieldsDTO;
import com.markguiang.backend.event.dto.event.EventIdsDTO;

import java.util.List;

public class UpdateEventDTO {
    @JsonUnwrapped
    @ValidRequired
    private EventIdsDTO eventIdsDTO;
    @JsonUnwrapped
    @ValidRequired
    private EventFieldsDTO eventFieldsDTO;
    @JsonProperty("scheduleList")
    @ValidRequired
    private List<UpdateScheduleDTO> updateScheduleDTOList;

    public EventIdsDTO getEventIdsDTO() {
        return eventIdsDTO;
    }

    public void setEventIdsDTO(EventIdsDTO eventIdsDTO) {
        this.eventIdsDTO = eventIdsDTO;
    }

    public EventFieldsDTO getEventFieldsDTO() {
        return eventFieldsDTO;
    }

    public void setEventFieldsDTO(EventFieldsDTO eventFieldsDTO) {
        this.eventFieldsDTO = eventFieldsDTO;
    }

    public List<UpdateScheduleDTO> getUpdateScheduleDTOList() {
        return updateScheduleDTOList;
    }

    public void setUpdateScheduleDTOList(List<UpdateScheduleDTO> updateScheduleDTOList) {
        this.updateScheduleDTOList = updateScheduleDTOList;
    }
}