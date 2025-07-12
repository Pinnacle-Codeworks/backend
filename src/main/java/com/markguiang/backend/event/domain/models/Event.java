package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.AggregateRoot;
import com.markguiang.backend.event.exceptions.DaysOnSameDateException;
import java.util.List;
import java.util.Objects;

// tenant-name unique constraint
public class Event extends AggregateRoot<Long> {
  private final String name;
  private final Boolean hasMultipleLocation;
  private String description;
  private String location;
  private String imgURL;
  private EventStatus eventStatus;
  private List<Day> days;

  public Event(
      String name,
      Boolean hasMultipleLocation,
      String description,
      String location,
      String imgURL,
      EventStatus eventStatus,
      List<Day> days) {
    setDescription(description);
    setLocation(location);
    setImgURL(imgURL);
    setEventStatus(eventStatus);

    this.name = validateName(name);
    this.hasMultipleLocation = hasMultipleLocation;
    this.days = validateDays(days);
  }

  private String validateName(String name) {
    requireNonNull(name, "name");
    return name;
  }

  private List<Day> validateDays(List<Day> days) {
    requireNonNull(days, "days");
    for (Day day : days) {
      Objects.requireNonNull(day);
    }
    if (!Day.allOnDifferentDates(days)) {
      throw new DaysOnSameDateException();
    }

    return days;
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

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setImgURL(String imgURL) {
    this.imgURL = imgURL;
  }

  public void setEventStatus(EventStatus eventStatus) {
    this.eventStatus = eventStatus;
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

  public String getImgURL() {
    return imgURL;
  }

  public EventStatus getEventStatus() {
    return eventStatus;
  }

  public List<Day> getDays() {
    return days;
  }
}
