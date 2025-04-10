package com.markguiang.backend.event.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.event.dto.schedule.ScheduleFieldsDTO;
import com.markguiang.backend.event.dto.schedule.ScheduleIdsDTO;

public class ScheduleResponseDTO {
    @JsonUnwrapped
    private ScheduleIdsDTO scheduleIdsDTO;
    @JsonUnwrapped
    private ScheduleFieldsDTO scheduleFieldsDTO;

    public ScheduleFieldsDTO getScheduleFieldsDTO() {
        return scheduleFieldsDTO;
    }

    public void setScheduleFieldsDTO(ScheduleFieldsDTO scheduleFieldsDTO) {
        this.scheduleFieldsDTO = scheduleFieldsDTO;
    }

    public ScheduleIdsDTO getScheduleIdsDTO() {
        return scheduleIdsDTO;
    }

    public void setScheduleIdsDTO(ScheduleIdsDTO scheduleIdsDTO) {
        this.scheduleIdsDTO = scheduleIdsDTO;
    }
}