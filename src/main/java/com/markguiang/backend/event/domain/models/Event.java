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
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Event extends AggregateRoot {
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

  private final String name;
  private final Boolean hasMultipleLocation;
  private String description;
  private String location;
  private URI imgURL;
  private EventStatus status;

  private final List<Day> days;

  public Event(
      UUID id,
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      URI imgURL,
      EventStatus status,
      List<Day> days) {
    super(id);
    this.name = prepareName(name);
    this.hasMultipleLocation = hasMultipleLocation;
    this.description = description;
    this.location = location;
    this.imgURL = imgURL;
    this.status = status;

    this.days = prepareDays(days);
  }

  public Event(
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      URI imgURL,
      EventStatus status,
      List<Day> days) {
    this(null, name, hasMultipleLocation, description, location, imgURL, status, days);
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

  public void addDay(Day newDay) {
    List<Day> copy = days.stream()
        .map(Day::new)
        .collect(Collectors.toList());
    copy.add(newDay);

    validateDays(copy);
    replaceDays(copy);
  }

  public void updateDay(Day day) {
    for (Day value : days) {
      if (value.getDate().equals(day.getDate())) {
        value.updateData(day.getLocation(), day.getDescription());
        return;
      }
    }
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

  private void replaceDays(List<Day> newDays) {
    days.clear();
    days.addAll(newDays);
  }
}
