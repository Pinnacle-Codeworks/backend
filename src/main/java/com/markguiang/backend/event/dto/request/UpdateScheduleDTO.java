package com.markguiang.backend.event.dto.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.event.dto.schedule.ScheduleFieldsDTO;
import com.markguiang.backend.event.dto.schedule.ScheduleIdsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class UpdateScheduleDTO {
    @JsonUnwrapped
    @Valid
    @NotNull
    private ScheduleIdsDTO scheduleIdsDTO;
    @JsonUnwrapped
    @Valid
    @NotNull
    private ScheduleFieldsDTO scheduleFieldsDTO;

    public ScheduleIdsDTO getScheduleIdsDTO() {
        return scheduleIdsDTO;
    }

    public void setScheduleIdsDTO(ScheduleIdsDTO scheduleIdsDTO) {
        this.scheduleIdsDTO = scheduleIdsDTO;
    }

    public ScheduleFieldsDTO getScheduleFieldsDTO() {
        return scheduleFieldsDTO;
    }

    public void setScheduleFieldsDTO(ScheduleFieldsDTO scheduleFieldsDTO) {
        this.scheduleFieldsDTO = scheduleFieldsDTO;
    }
}