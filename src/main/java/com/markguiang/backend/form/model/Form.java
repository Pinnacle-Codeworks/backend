package com.markguiang.backend.form.model;

import com.markguiang.backend.base.AbstractBaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "eventId", "name" }) })
public class Form extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    // foreignKeys
    @Column(updatable = false, nullable = false)
    private Long eventId;

    // fields
    @Column(nullable = false)
    private String name;

    private String description;

    @Transient
    private List<Field> fieldList;

    @OneToMany(mappedBy = "formId", fetch = FetchType.LAZY)
    private List<FormAnswers> formAnswersList;

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

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

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public List<FormAnswers> getFormAnswersList() {
        return formAnswersList;
    }

    public void setFormAnswersList(List<FormAnswers> formAnswersList) {
        this.formAnswersList = formAnswersList;
    }
}
