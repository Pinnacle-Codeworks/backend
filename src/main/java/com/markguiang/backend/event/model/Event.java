package com.markguiang.backend.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import com.markguiang.backend.form.model.Form;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Entity
public class Event {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @NotNull
    private String name;
    private String description;
    private Calendar dateTime;
    private String location;
    private Boolean hasMultipleLocation;
    @JdbcTypeCode(SqlTypes.JSON)
    @JsonDeserialize(keyUsing = LocalDateKeyDeserializer.class)
    private Map<LocalDate, String> dateLocationMap;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eventId")
    private List<Schedule> scheduleList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId")
    private List<Form> formList;
    private Long companyId;

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
    public Boolean getHasMultipleLocation() {
        return hasMultipleLocation;
    }
    public void setHasMultipleLocation(Boolean hasMultipleLocation) {
        this.hasMultipleLocation = hasMultipleLocation;
    }
    public Map<LocalDate, String> getDateLocationMap() {
        return dateLocationMap;
    }
    public void setDateLocationMap(Map<LocalDate, String> dateLocationMap) {
        this.dateLocationMap = dateLocationMap;
    }
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }
    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
