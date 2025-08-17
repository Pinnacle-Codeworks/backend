package com.markguiang.backend.event.domain.adapters.http.dto;

import com.markguiang.backend.event.domain.models.Event;
import java.net.URI;
import java.util.UUID;

public record EventResponseWithoutDaysDTO(
    UUID id,
    String name,
    Boolean hasMultipleLocation,
    String description,
    String location,
    String imgURL,
    Event.EventStatus eventStatus) {

  public static EventResponseWithoutDaysDTO fromEvent(Event event) {
    return new EventResponseWithoutDaysDTO(
        event.getId(),
        event.getName(),
        event.getHasMultipleLocation(),
        event.getDescription(),
        event.getLocation(),
        event.getImgURL(),
        event.getStatus());
  }
}
