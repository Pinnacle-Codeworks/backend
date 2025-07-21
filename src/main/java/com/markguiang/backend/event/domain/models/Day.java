package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.model.LocalEntity;
import com.markguiang.backend.event.exceptions.AgendaNotFoundException;
import com.markguiang.backend.event.exceptions.AgendasOnDifferentDateException;
import com.markguiang.backend.event.exceptions.DateHasTimeException;
import com.markguiang.backend.event.exceptions.OverlappingAgendaTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day extends LocalEntity {
  public static boolean allOnDifferentDates(List<Day> days) {
    if (days == null || days.isEmpty()) {
      return true;
    }
    Set<LocalDate> uniqueDays = new HashSet<>();
    for (Day day : days) {
      if (!uniqueDays.add(day.getDate().toLocalDate())) {
        return false;
      }
    }
    return true;
  }

  private String description;
  private String location;
  private final OffsetDateTime date;

  private final List<Agenda> agendas;

  public Day(Day day) {
    this(day.getId(), day.getLocation(), day.getDate(), day.getAgendas(), day.getDescription());
  }

  public Day(
      UUID ID, String location, OffsetDateTime date, List<Agenda> agendas, String description) {
    super(ID);
    this.date = prepareDate(date);
    this.agendas = prepareAgendas(agendas);
    this.location = location;
    this.description = description;
  }

  public Day(String location, OffsetDateTime date, List<Agenda> agendas, String description) {
    this(null, location, date, agendas, description);
  }

  public void addAgenda(Agenda agenda) {
    Objects.requireNonNull(agenda);
    mutateAgendas(list -> list.add(agenda));
  }

  public void removeAgenda(Agenda agenda) {
    Objects.requireNonNull(agenda);
    mutateAgendas(list -> {
      if (!list.remove(agenda)) {
        throw new AgendaNotFoundException(agenda.getStartDate());
      }
      return true;
    });
  }

  public String getLocation() {
    return location;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public List<Agenda> getAgendas() {
    return Collections.unmodifiableList(agendas);
  }

  public void updateData(String location, String description) {
    this.location = location;
    this.description = description;
  }

  private void mutateAgendas(Function<List<Agenda>, Boolean> mutator) {
    List<Agenda> copy = agendas.stream()
        .map(Agenda::new)
        .collect(Collectors.toList());

    boolean changed = mutator.apply(copy);
    if (!changed)
      return;

    validateAgendas(copy);
    agendas.clear();
    agendas.addAll(copy);
    sortAgendas();
  }

  private void validateDate(OffsetDateTime date) {
    requireNonNull(date, "date");
    OffsetDateTime dateOnly = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    if (!date.equals(dateOnly)) {
      throw new DateHasTimeException();
    }
  }

  private OffsetDateTime prepareDate(OffsetDateTime date) {
    validateDate(date);
    return date;
  }

  private void validateAgendas(List<Agenda> agendas) {
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
  }

  private List<Agenda> prepareAgendas(List<Agenda> agendas) {
    validateAgendas(agendas);
    return new ArrayList<>(
        agendas.stream()
            .map(Agenda::new)
            .sorted(Comparator.comparing(Agenda::getStartDate))
            .collect(Collectors.toList()));
  }

  private void sortAgendas() {
    agendas.sort(Comparator.comparing(Agenda::getStartDate));
  }
}
