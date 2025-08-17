package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.exceptions.InvalidDateRangeException;
import com.markguiang.backend.base.model.ValueObject;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.markguiang.backend.utils.Utils.onSameDate;

public class Agenda implements ValueObject {
  public static boolean hasOverlappingTimes(List<Agenda> agendaList) {
    if (agendaList == null || agendaList.isEmpty()) {
      return false;
    }

    List<Agenda> copy = new ArrayList<>(List.copyOf(agendaList));
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

  public static boolean allOnDate(List<Agenda> agendaList, OffsetDateTime date) {
    Objects.requireNonNull(date);
    for (Agenda agenda : agendaList) {
      if (!onSameDate(agenda.getStartDate(), date)
          || !onSameDate(agenda.getEndDate(), date)) {
        return false;
      }
    }
    return true;
  }

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

  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public String getLocation() {
    return location;
  }

  private void validateDates(OffsetDateTime startDate, OffsetDateTime endDate) {
    Objects.requireNonNull(startDate);
    Objects.requireNonNull(endDate);

    if (startDate.isAfter(endDate)) {
      throw new InvalidDateRangeException();
    }
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (!(other instanceof Agenda)) return false;
    Agenda oAgenda = (Agenda) other;
    if (!Objects.equals(this.startDate, oAgenda.getStartDate())
        || !Objects.equals(this.endDate, oAgenda.getEndDate())
        || !Objects.equals(this.location, oAgenda.getLocation())) return false;
    return true;
  }
}
