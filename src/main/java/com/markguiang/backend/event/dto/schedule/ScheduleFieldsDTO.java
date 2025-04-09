package com.markguiang.backend.event.dto.schedule;

import jakarta.validation.constraints.NotNull;

import java.util.Calendar;

public record ScheduleFieldsDTO(@NotNull Calendar startDate,
                                @NotNull Calendar endDate,
                                String location) {
}