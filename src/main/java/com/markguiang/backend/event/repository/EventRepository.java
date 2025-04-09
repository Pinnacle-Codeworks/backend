package com.markguiang.backend.event.repository;

import com.markguiang.backend.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Boolean existsByNameAndCompanyId(String name, Long companyId);
}