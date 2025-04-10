package com.markguiang.backend.event.repository;

import com.markguiang.backend.event.model.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    @Query(value="SELECT s FROM Schedule s WHERE s.event.eventId = :eventId")
    List<Schedule> findScheduleByEventId(Long eventId);

}