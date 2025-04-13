package com.markguiang.backend.event.dto.mapper;

import com.markguiang.backend.event.dto.request.CreateEventDTO;
import com.markguiang.backend.event.dto.request.UpdateEventDTO;
import com.markguiang.backend.event.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface EventRequestMapper {
    @Mapping(source = "createEventScheduleDTOList", target = "scheduleList")
    Event createEventDTOtoEvent(CreateEventDTO createEventDTO);

    @Mapping(source = "updateEventScheduleDTOList", target = "scheduleList")
    Event updateEventDTOtoEvent(UpdateEventDTO updateEventDTO);
}