package com.markguiang.backend.event.domain.adapters.jdbi;

import com.markguiang.backend.event.domain.adapters.jdbi.mappers.AgendaInsertDto;
import com.markguiang.backend.event.domain.adapters.jdbi.mappers.EventReducer;
import com.markguiang.backend.event.domain.adapters.jdbi.mappers.EventRow;
import com.markguiang.backend.event.domain.models.Event;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RegisterConstructorMapper(EventRow.class)
public interface EventDao {
  @SqlQuery("""
          SELECT EXISTS (
            SELECT 1 FROM event WHERE name = :name AND tenant_id = :tenantId
          )
      """)
  boolean existsByName(@Bind("tenantId") Long tenantId, @Bind("name") String name);

  @SqlQuery("""
          SELECT
            e.id AS event_id, e.name, e.has_multiple_location, e.description, e.location, e.img_url, e.status,
            d.id AS day_id, d.location AS day_location, d.date AS day_date, d.description as day_description,
            a.start_date AS agenda_start, a.end_date AS agenda_end, a.location AS agenda_location
          FROM event e
          LEFT JOIN day d ON d.event_id = e.id
          LEFT JOIN agenda a ON a.day_id = d.id
          WHERE e.id = :id AND e.tenant_id = :tenantId
      """)
  @UseRowReducer(EventReducer.class)
  Optional<Event> findEventAggregate(@Bind("tenantId") Long tenantId, @Bind("id") UUID id);

  @SqlQuery("""
          SELECT
              e.id AS event_id,
              e.name,
              e.has_multiple_location,
              e.description,
              e.location,
              e.img_url,
              e.status
          FROM event e
          WHERE e.tenant_id = :tenantId
          ORDER BY e.<sortColumn> <sortDirection>
          LIMIT :size OFFSET :offset
      """)
  @UseRowReducer(EventReducer.class)
  List<Event> findEventsWithPagination(
      @Bind("tenantId") Long tenantId,
      @Bind("size") int size,
      @Bind("offset") int offset,
      @Define("sortColumn") String sortColumn,
      @Define("sortDirection") String sortDirection);

  @SqlQuery("SELECT COUNT(*) FROM event e WHERE e.tenant_id = :tenantId")
  int countEvents(@Bind("tenantId") Long tenantId);

  @SqlUpdate("""
          INSERT INTO event (id, tenant_id, name, has_multiple_location, description, location, img_url, status)
          VALUES (:id, :tenantId, :name, :hasMultipleLocation, :description, :location, :imgURL, :status)
      """)
  void insertEvent(@Bind("tenantId") Long tenantId, @BindBean Event event);

  @SqlUpdate("""
          UPDATE event
          SET name = :name,
              has_multiple_location = :hasMultipleLocation,
              description = :description,
              location = :location,
              img_url = :imgURL,
              status = :status
          WHERE id = :id AND tenant_id = :tenantId
      """)
  void updateEvent(@Bind("tenantId") Long tenantId, @BindBean Event event);

  @SqlUpdate("""
      UPDATE event
      SET img_url = :imgURL
      WHERE id = :id AND tenant_id = :tenantId
      """)
  void updateImageUrl(@Bind("tenantId") Long tenantId, @Bind("id") UUID id, @Bind("imgURL") URI imgURL);

  @SqlUpdate("""
          INSERT INTO day (id, tenant_id, event_id, location, date, description)
          VALUES (:id, :tenantId, :eventId, :location, :date, :description)
      """)
  void insertDay(
      @Bind("tenantId") Long tenantId,
      @Bind("id") UUID id,
      @Bind("eventId") UUID eventId,
      @Bind("location") String location,
      @Bind("date") OffsetDateTime date,
      @Bind("description") String description);

  @SqlUpdate("""
          INSERT INTO agenda (id, tenant_id, day_id, start_date, end_date, location)
          VALUES (:id, :tenantId, :dayId, :startDate, :endDate, :location)
      """)
  void insertAgenda(@Bind("tenantId") Long tenantId, @BindBean AgendaInsertDto agenda);

  @SqlUpdate("""
          DELETE FROM agenda
          WHERE day_id = :dayId
          AND start_date = :startDate
          AND end_date = :endDate
          AND tenant_id = :tenantId
      """)
  void removeAgenda(
      @Bind("tenantId") Long tenantId,
      @Bind("dayId") UUID dayId,
      @Bind("startDate") OffsetDateTime startDate,
      @Bind("endDate") OffsetDateTime endDate);

  @SqlUpdate("""
          UPDATE day
          SET location = :location,
              description = :description
          WHERE id = :id AND tenant_id = :tenantId
      """)
  void updateDay(
      @Bind("tenantId") Long tenantId,
      @Bind("id") UUID id,
      @Bind("location") String location,
      @Bind("description") String description);

}
