package com.markguiang.backend.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"company", "name"})
)
public class Event {
    @Id
    @JsonIgnore
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @NotNull
    private String company;
    @NotNull
    private String name;
    private String description;
    private Calendar dateTime;
    private String location;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "event_id")
    private List<Schedule> timetable;

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Calendar getDateTime() {
        return dateTime;
    }
    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public List<Schedule> getTimetable() {
        return timetable;
    }
    public void setTimetable(List<Schedule> timetable) {
        this.timetable = timetable;
    }
}
