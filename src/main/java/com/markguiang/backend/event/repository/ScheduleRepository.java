package com.markguiang.backend.event.repository;

import com.markguiang.backend.event.model.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
}
