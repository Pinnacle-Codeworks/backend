package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.LocalEntity;
import com.markguiang.backend.event.exceptions.AgendasOnDifferentDateException;
import com.markguiang.backend.event.exceptions.DateHasTimeException;
import com.markguiang.backend.event.exceptions.OverlappingAgendaTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day extends LocalEntity<Long> {
  private String location;
  private final OffsetDateTime date;
  private final List<Agenda> agendas;

  public Day(String location, OffsetDateTime date, List<Agenda> agendas) {
    this.date = validateDate(date);
    this.agendas = prepareAgendaList(validateAgendas(agendas));
    this.location = location;
  }

  public Day withAddedAgenda(Agenda nAgenda) {
    Objects.requireNonNull(nAgenda);

    List<Agenda> nAgendas = new ArrayList<>(agendas);
    nAgendas.add(nAgenda);

    return new Day(location, date, nAgendas);
  }

  public Day withRemovedAgenda(Agenda agenda) {
    Objects.requireNonNull(agenda);

    List<Agenda> nAgendas = agendas.stream().filter(a -> !a.equals(agenda)).collect(Collectors.toList());
    return new Day(location, date, nAgendas);
  }

  public Day withAgendas(List<Agenda> nAgendas) {
    Objects.requireNonNull(nAgendas);

    return new Day(location, date, nAgendas);
  }

  public static boolean allOnDifferentDates(List<Day> days) {
    if (days == null || days.isEmpty()) {
      return false;
    }

    Set<LocalDate> uniqueDays = new HashSet<>();
    for (Day day : days) {
      if (!uniqueDays.add(day.getDate().toLocalDate())) {
        return false;
      }
    }

    return true;
  }

  private OffsetDateTime validateDate(OffsetDateTime date) {
    requireNonNull(date, "date");

    OffsetDateTime dateOnly = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    if (!date.equals(dateOnly)) {
      throw new DateHasTimeException();
    }
    return date;
  }

  private List<Agenda> validateAgendas(List<Agenda> agendas) {
    requireNonNull(agendas, "agendas");

    for (Agenda agenda : agendas) {
      Objects.requireNonNull(agenda);
    }
    if (!Agenda.allOnDate(agendas, date)) {
      throw new AgendasOnDifferentDateException();
    }
    if (Agenda.hasOverlappingTimes(agendas)) {
      throw new OverlappingAgendaTimeException();
    }

    return agendas;
  }

  private List<Agenda> prepareAgendaList(List<Agenda> agendaList) {
    List<Agenda> sorted = new ArrayList<>(agendaList);
    sorted.sort(Comparator.comparing(Agenda::getStartDate));
    return List.copyOf(sorted);
  }

  public OffsetDateTime getDate() {
    return date;
  }
}
