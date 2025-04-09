package com.markguiang.backend.event.dto.schedule;

import java.util.Calendar;

public record ScheduleFieldsDTO(Calendar startDate,
                                Calendar endDate,
                                String location) {
}