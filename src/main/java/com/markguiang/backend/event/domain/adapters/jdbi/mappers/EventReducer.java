package com.markguiang.backend.event.domain.adapters.jdbi.mappers;

import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.models.Event.EventStatus;
import java.net.URI;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

public class EventReducer implements LinkedHashMapRowReducer<UUID, Event> {

  @Override
  public void accumulate(Map<UUID, Event> map, RowView rowView) {
    UUID eventId = rowView.getColumn("event_id", UUID.class);

    Event event =
        map.computeIfAbsent(
            eventId,
            id ->
                new Event(
                    id,
                    rowView.getColumn("name", String.class),
                    rowView.getColumn("has_multiple_location", Boolean.class),
                    rowView.getColumn("description", String.class),
                    rowView.getColumn("location", String.class),
                    rowView.getColumn("img_url", URI.class),
                    EventStatus.valueOf(rowView.getColumn("status", String.class)),
                    new ArrayList<>()));

    UUID dayId = rowView.getColumn("day_id", UUID.class);
    if (dayId != null) {
      Day day =
          event.getDays().stream()
              .filter(d -> d.getId().equals(dayId))
              .findFirst()
              .orElseGet(
                  () -> {
                    Day newDay =
                        new Day(
                            dayId,
                            rowView.getColumn("day_location", String.class),
                            rowView
                                .getColumn("day_date", Timestamp.class)
                                .toInstant()
                                .atOffset(ZoneOffset.UTC),
                            new ArrayList<>(),
                            rowView.getColumn("day_description", String.class));
                    event.addDay(newDay);
                    return newDay;
                  });

      Timestamp start = rowView.getColumn("agenda_start", Timestamp.class);
      if (start != null) {
        Agenda agenda =
            new Agenda(
                start.toInstant().atOffset(ZoneOffset.UTC),
                rowView
                    .getColumn("agenda_end", Timestamp.class)
                    .toInstant()
                    .atOffset(ZoneOffset.UTC),
                rowView.getColumn("agenda_location", String.class));
        day.addAgenda(agenda);
      }
    }
  }
}
