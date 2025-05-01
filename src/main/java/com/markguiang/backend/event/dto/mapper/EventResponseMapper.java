package com.markguiang.backend.event.dto.mapper;

import com.markguiang.backend.event.dto.response.EventResponseDTO;
import com.markguiang.backend.event.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EventResponseMapper {
    @Mapping(source = "scheduleList", target = "scheduleResponseDTOList")
    EventResponseDTO eventToEventResponseDTO(Event event);
    
}