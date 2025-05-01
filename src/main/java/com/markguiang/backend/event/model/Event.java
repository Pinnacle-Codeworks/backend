package com.markguiang.backend.event.model;

import com.markguiang.backend.base.AbstractBaseEntity;
import com.markguiang.backend.event.enum_.EventStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id", "name"})})
public class Event extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    // fields
    @Column(nullable = false)
    private String name;
    private String description;
    private String location;
    private Boolean hasMultipleLocation;
    private String imgURL;
    private EventStatus eventStatus;

    @JdbcTypeCode(SqlTypes.JSON)
    @JsonDeserialize(keyUsing = LocalDateKeyDeserializer.class)
    private Map<LocalDate, String> dateLocationMap;

    // children
    @Transient
    private List<Schedule> scheduleList;

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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
}
