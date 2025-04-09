package com.markguiang.backend.event.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;

public record EventFieldsDTO(@NotNull String name,
                             String description,
                             Calendar dateTime,
                             String location,
                             @JsonProperty(defaultValue = "false")
                              Boolean hasMultipleLocation,
                             Map<LocalDate, String> dateLocationMap) {
}