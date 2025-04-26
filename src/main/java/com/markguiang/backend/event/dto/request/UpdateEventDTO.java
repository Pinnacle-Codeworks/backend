package com.markguiang.backend.event.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.annotation.ValidRequired;
import jakarta.validation.constraints.NotNull;

import java.util.Calendar;
import java.util.List;

public record UpdateEventDTO(
        @NotNull Long eventId,
        @NotNull String name,
        String description,
        String location,
        Boolean hasMultipleLocation,
        @JsonProperty("scheduleList")
        @ValidRequired
        List<UpdateEventScheduleDTO> updateEventScheduleDTOList
) {
    public record UpdateEventScheduleDTO(
            @NotNull Long scheduleId,
            @NotNull Calendar startDate,
            @NotNull Calendar endDate,
            String location
    ) {
    }
}