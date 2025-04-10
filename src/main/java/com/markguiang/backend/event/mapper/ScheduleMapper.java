package com.markguiang.backend.event.mapper;

import com.markguiang.backend.event.dto.request.UpdateScheduleDTO;
import com.markguiang.backend.event.dto.response.ScheduleResponseDTO;
import com.markguiang.backend.event.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source= "event.eventId", target = "scheduleIdsDTO.eventId")
    @Mapping(source = "scheduleId", target = "scheduleIdsDTO.scheduleId")
    @Mapping(source = ".", target = "scheduleFieldsDTO")
    ScheduleResponseDTO scheduleToScheduleResponseDTO(Schedule schedule);

    @Mapping(source = "scheduleIdsDTO.scheduleId", target = "scheduleId")
    @Mapping(source = "scheduleIdsDTO.eventId", target = "event.eventId")
    @Mapping(source = "scheduleFieldsDTO", target = ".")
    Schedule updateScheduleDTOtoSchedule(UpdateScheduleDTO updateScheduleDTO);
}