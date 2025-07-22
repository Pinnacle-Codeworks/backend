package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.model.AggregateRoot;
import com.markguiang.backend.base.model.IdentifiableDomainObject;
import com.markguiang.backend.event.exceptions.DayNotFoundException;
import com.markguiang.backend.event.exceptions.DaysOnSameDateException;
import com.markguiang.backend.event.utils.DateUtils;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Event extends AggregateRoot {

  public enum EventStatus {
    DRAFT,
    LIVE,
    SCHEDULED,
    OPEN,
    POST_EVENT,
    COMPLETED,
    CANCELLED,
    POSTPONED
  }

  public static Event create(
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      URI imgURL,
      EventStatus status,
      List<Day> days) {
    return new Event(null, name, hasMultipleLocation, description, location, imgURL, status, days);
  }

  public static Event loadFromPersistence(
      UUID id,
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      URI imgURL,
      EventStatus status,
      List<Day> days) {
    return new Event(id, name, hasMultipleLocation, description, location, imgURL, status, days);
  }

  private final String name;
  private final Boolean hasMultipleLocation;
  private String description;
  private String location;

  private URI imgURL;

  private EventStatus status;

  private final List<Day> days;

  private Event(
      UUID id,
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      URI imgURL,
      EventStatus status,
      List<Day> days) {
    super(id);
    this.name = requireNonNull(name, "name");
    this.hasMultipleLocation = hasMultipleLocation;
    this.description = description;
    this.location = location;
    this.imgURL = imgURL;
    this.status = status;

    this.days = copyAndValidateDays(days);
  }

  public Day addAgendaToDay(Agenda agenda) {
    requireNonNull(agenda, "agenda");
    Day day = getDayWithDate(agenda.getStartDate());
    day.addAgenda(agenda);
    return day;
  }

  public Day removeAgendaFromDay(Agenda agenda) {
    requireNonNull(agenda, "agenda");
    Day day = getDayWithDate(agenda.getStartDate());
    day.removeAgenda(agenda);
    return day;
  }

  public void addDay(Day newDay) {
    requireNonNull(newDay, "day");
    List<Day> updated = new ArrayList<>(copyAndCloneDays());
    updated.add(Day.copyFrom(newDay));

    validateDays(updated);
    replaceDays(updated);
  }

  public void updateDayDetails(UUID dayId, OffsetDateTime date, String location, String description) {
    requireNonNull(dayId, "dayId");
    requireNonNull(date, "date");

    for (Day existing : days) {
      if (existing.getId().equals(dayId) && existing.getDate().equals(date)) {
        existing.updateDetails(location, description);
        return;
      }
    }
    throw new DayNotFoundException(date);
  }

  public void updateDetails(String description, String location) {
    this.description = description;
    this.location = location;
  }

  public void updateImage(URI imgUrl) {
    this.imgURL = imgUrl;
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

  public EventStatus getStatus() {
    return status;
  }

  public List<Day> getDays() {
    return Collections.unmodifiableList(days);
  }

  private Day getDayWithDate(OffsetDateTime date) {
    for (Day day : days) {
      if (DateUtils.onSameDate(day.getDate(), date)) {
        return day;
      }
    }
    throw new DayNotFoundException(date);
  }

  private void validateDays(List<Day> days) {
    requireNonNull(days, "days");
    for (Day day : days) {
      requireNonNull(day, "day");
    }
    IdentifiableDomainObject.validateForDuplicateKeys(days);

    if (!Day.allOnDifferentDates(days)) {
      throw new DaysOnSameDateException();
    }
  }

  private List<Day> copyAndValidateDays(List<Day> days) {
    validateDays(days);
    return days.stream()
        .map(Day::copyFrom)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Day> copyAndCloneDays() {
    return days.stream()
        .map(Day::copyFrom)
        .collect(Collectors.toList());
  }

  private void replaceDays(List<Day> newDays) {
    days.clear();
    days.addAll(newDays);
  }
}
