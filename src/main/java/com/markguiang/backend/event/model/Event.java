package com.markguiang.backend.event.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import com.markguiang.backend.base.AbstractBaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "tenantId", "name" }) })
public class Event extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    // fields
    @Column(nullable = false)
    private String name;
    private String description;
    private Calendar dateTime;
    private String location;
    private Boolean hasMultipleLocation;
    @JdbcTypeCode(SqlTypes.JSON)
    @JsonDeserialize(keyUsing = LocalDateKeyDeserializer.class)
    private Map<LocalDate, String> dateLocationMap;

    // children
    @Transient
    private List<Schedule> scheduleList;

    public Map<LocalDate, String> getDateLocationMap() {
        return dateLocationMap;
    }

    public void setDateLocationMap(Map<LocalDate, String> dateLocationMap) {
        this.dateLocationMap = dateLocationMap;
    }

    public Boolean getHasMultipleLocation() {
        return hasMultipleLocation;
    }

    public void setHasMultipleLocation(Boolean hasMultipleLocation) {
        this.hasMultipleLocation = hasMultipleLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
