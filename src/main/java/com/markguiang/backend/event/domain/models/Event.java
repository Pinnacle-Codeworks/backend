package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.model.AggregateRoot;
import com.markguiang.backend.base.model.IdentifiableDomainObject;
import com.markguiang.backend.event.exceptions.AgendasOnDifferentDateException;
import com.markguiang.backend.event.exceptions.DaysOnSameDateException;
import com.markguiang.backend.event.utils.DateUtils;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Event extends AggregateRoot {
  private final String name;
  private final Boolean hasMultipleLocation;
  private String description;
  private String location;
  private URI imgURL;
  private EventStatus eventStatus;
  private List<Day> days;

  public Event(
      UUID id,
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      URI imgURL,
      EventStatus eventStatus,
      List<Day> days) {
    super(id);
    this.name = prepareName(name);
    this.hasMultipleLocation = hasMultipleLocation;
    this.description = description;
    this.location = location;
    this.imgURL = imgURL;
    this.eventStatus = eventStatus;

    this.days = prepareDays(days);
  }

  public Event(
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      URI imgURL,
      EventStatus eventStatus,
      List<Day> days) {
    this(null, name, hasMultipleLocation, description, location, imgURL, eventStatus, days);
  }

  public Day addAgendaToDay(Agenda agenda) {
    Day day = getDayWithDate(agenda.getStartDate());
    day.addAgenda(agenda);
    return day;
  }

  public Day removeAgendaFromDay(Agenda agenda) {
    Day day = getDayWithDate(agenda.getStartDate());
    day.removeAgenda(agenda);
    return day;
  }

  private Day getDayWithDate(OffsetDateTime date) {
    for (Day day : days) {
      if (DateUtils.onSameDate(day.getDate(), date)) {
        return day;
      }
    }
    throw new AgendasOnDifferentDateException();
  }

  public void updateDay(Day day) {
    for (Day value : days) {
      if (value.getDate().equals(day.getDate())) {
        value.updateData(day.getLocation(), day.getDescription());
        return;
      }
    }
  }

  private String prepareName(String name) {
    requireNonNull(name, "name");
    return name;
  }

  private void validateDays(List<Day> days) {
    requireNonNull(days, "days");
    for (Day day : days) {
      Objects.requireNonNull(day);
    }
    IdentifiableDomainObject.validateForDuplicateKeys(days);

    if (!Day.allOnDifferentDates(days)) {
      throw new DaysOnSameDateException();
    }
  }

  private List<Day> prepareDays(List<Day> days) {
    validateDays(days);
    List<Day> copy = new ArrayList<>();
    for (Day day : days) {
      copy.add(new Day(day));
    }
    return copy;
  }

  public enum EventStatus {
    DRAFT,
    LIVE,
    SCHEDULED,
    OPEN, // REGISTRATION OPEN
    POST_EVENT, // POST EVENT REVIEW
    COMPLETED,
    CANCELLED,
    POSTPONED
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getLocation() {
    return location;
  }

  public Boolean getHasMultipleLocation() {
    return hasMultipleLocation;
  }

  public URI getImgURL() {
    return imgURL;
  }

  public EventStatus getEventStatus() {
    return eventStatus;
  }

  public List<Day> getDays() {
    return Collections.unmodifiableList(days);
  }
}
