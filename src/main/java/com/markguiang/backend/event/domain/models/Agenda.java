package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.exceptions.InvalidDateRangeException;
import com.markguiang.backend.base.model.ValueObject;
import com.markguiang.backend.event.utils.DateUtils;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Agenda implements ValueObject {
  private final OffsetDateTime startDate;
  private final OffsetDateTime endDate;
  private final String location;

  public Agenda(Agenda agenda) {
    this(agenda.getStartDate(), agenda.getEndDate(), agenda.getLocation());
  }

  public Agenda(OffsetDateTime startDate, OffsetDateTime endDate, String location) {
    super();
    validateDates(startDate, endDate);

    this.startDate = startDate;
    this.endDate = endDate;
    this.location = location;
  }

  // TODO: add unit test
  public static boolean hasOverlappingTimes(List<Agenda> agendaList) {
    List<Agenda> copy = new ArrayList<>(agendaList);
    copy.sort(Comparator.comparing(Agenda::getStartDate));

    for (int i = 1; i < copy.size(); i++) {
      Agenda current = copy.get(i);
      Agenda before = copy.get(i - 1);

      if (before.getEndDate().isAfter(current.getStartDate())) {
        return true;
      }
    }
    return false;
  }

  // TODO add unit test
  public static boolean allOnDate(List<Agenda> agendaList, OffsetDateTime date) {
    for (Agenda agenda : agendaList) {
      if (!DateUtils.onSameDate(agenda.getStartDate(), date)
          || !DateUtils.onSameDate(agenda.getEndDate(), date)) {
        return false;
      }
    }
    return true;
  }

  private void validateDates(OffsetDateTime startDate, OffsetDateTime endDate) {
    Objects.requireNonNull(startDate);
    Objects.requireNonNull(endDate);

    if (startDate.isAfter(endDate)) {
      throw new InvalidDateRangeException();
    }
  }

  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public String getLocation() {
    return location;
  }
}
