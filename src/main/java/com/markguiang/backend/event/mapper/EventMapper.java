package com.markguiang.backend.event.mapper;

import com.markguiang.backend.event.dto.request.CreateEventDTO;
import com.markguiang.backend.event.dto.request.UpdateEventDTO;
import com.markguiang.backend.event.dto.response.EventResponseDTO;
import com.markguiang.backend.event.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ScheduleMapper.class)
public interface EventMapper {

    @Mapping(source = "eventFieldsDTO", target = ".")
    @Mapping(source = "scheduleFieldsDTOList", target = "scheduleList")
    Event createEventDTOtoEvent(CreateEventDTO createEventDTO);

    @Mapping(source = "eventIdsDTO", target = ".")
    @Mapping(source = "eventFieldsDTO", target = ".")
    @Mapping(source = "updateScheduleDTOList", target = "scheduleList")
    Event updateEventDTOtoEvent(UpdateEventDTO updateEventDTO);

    @Mapping(source = ".", target = "eventFieldsDTO")
    @Mapping(source = ".", target = "eventIdsDTO")
    @Mapping(source = "scheduleList", target = "scheduleResponseDTOList")
    EventResponseDTO eventToEventResponseDTO(Event event);
}