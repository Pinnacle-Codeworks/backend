package com.markguiang.backend.event.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.annotation.ValidRequired;
import com.markguiang.backend.event.enum_.EventStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Calendar;
import java.util.List;

public record CreateEventDTO(
        @NotNull String name,
        String description,
        String location,

        String imgURL,
        EventStatus eventStatus,
        Boolean hasMultipleLocation,
        @JsonProperty("scheduleList")
        @ValidRequired
        List<CreateEventScheduleDTO> createEventScheduleDTOList
) {
    public record CreateEventScheduleDTO(
            @NotNull Calendar startDate,
            @NotNull Calendar endDate,
            String location
    ) {
    }
}