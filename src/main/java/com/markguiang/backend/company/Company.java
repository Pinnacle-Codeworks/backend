package com.markguiang.backend.company;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    // mappings
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private List<Event> eventList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private List<User> userList;

    // fields
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}