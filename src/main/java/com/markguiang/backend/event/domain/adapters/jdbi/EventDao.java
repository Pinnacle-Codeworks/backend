package com.markguiang.backend.event.domain.adapters.jdbi;

import com.markguiang.backend.event.domain.adapters.jdbi.mappers.AgendaInsertDto;
import com.markguiang.backend.event.domain.adapters.jdbi.mappers.EventReducer;
import com.markguiang.backend.event.domain.adapters.jdbi.mappers.EventRow;
import com.markguiang.backend.event.domain.models.Event;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

@RegisterConstructorMapper(EventRow.class)
public interface EventDao {

  @SqlQuery("""
          SELECT EXISTS (
            SELECT 1 FROM events WHERE name = :name AND tenant_id = :tenantId
          )
      """)
  boolean existsByName(@Bind("name") String name);

  @SqlQuery("""
          SELECT
            e.id AS event_id, e.name, e.has_multiple_location, e.description, e.location, e.img_url, e.status,
            d.id AS day_id, d.location AS day_location, d.date AS day_date,
            a.start_date AS agenda_start, a.end_date AS agenda_end, a.location AS agenda_location
          FROM events e
          LEFT JOIN days d ON d.event_id = e.id
          LEFT JOIN agendas a ON a.day_id = d.id
          WHERE e.id = :id AND e.tenant_id = :tenantId
      """)
  @UseRowReducer(EventReducer.class)
  Optional<Event> findEventAggregate(@Bind("id") UUID id);

  @SqlUpdate("""
          INSERT INTO events (id, tenant_id, name, has_multiple_location, description, location, img_url, status)
          VALUES (:id, :tenantId, :name, :hasMultipleLocation, :description, :location, :imgURL, :status)
      """)
  void insertEvent(@BindBean Event event);

  @SqlUpdate("""
          UPDATE events
          SET name = :name,
              has_multiple_location = :hasMultipleLocation,
              description = :description,
              location = :location,
              img_url = :imgURL,
              status = :status
          WHERE id = :id AND tenant_id = :tenantId
      """)
  void updateEvent(@BindBean Event event);

  @SqlUpdate("""
          INSERT INTO days (id, tenant_id, event_id, location, date, description)
          VALUES (:id, :tenantId, :eventId, :location, :date, :description)
      """)
  void insertDay(
      @Bind("id") UUID id,
      @Bind("eventId") UUID eventId,
      @Bind("location") String location,
      @Bind("date") OffsetDateTime date,
      @Bind("description") String description);

  // @SqlBatch("""
  // INSERT INTO agendas (id, tenant_id, day_id, start_date, end_date, location)
  // VALUES (:id, :tenantId, :dayId, :startDate, :endDate, :location)
  // """)
  // void insertAgendas(@BindBean List<AgendaInsertDto> agendas);

  @SqlUpdate("""
          INSERT INTO agendas (id, tenant_id, day_id, start_date, end_date, location)
          VALUES (:id, :tenantId, :dayId, :startDate, :endDate, :location)
      """)
  void insertAgenda(@BindBean AgendaInsertDto agenda);

  @SqlUpdate("""
          DELETE FROM agendas
          WHERE day_id = :dayId
          AND start_date = :startDate
          AND end_date = :endDate
          AND tenant_id = :tenantId
      """)
  void removeAgenda(
      @Bind("dayId") UUID dayId,
      @Bind("startDate") OffsetDateTime startDate,
      @Bind("endDate") OffsetDateTime endDate);

  @SqlUpdate("""
          UPDATE days
          SET location = :location,
              description = :description
          WHERE id = :id AND tenant_id = :tenantId
      """)
  void updateDay(
      @Bind("id") UUID id,
      @Bind("location") String location,
      @Bind("description") String description);
}
