package com.markguiang.backend.form.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"companyId", "eventId", "name"})
})
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    // foreignKeys
    @Column(updatable = false, nullable = false)
    private Long companyId;
    @Column(updatable = false, nullable = false)
    private Long eventId;

    // fields
    @Column(nullable = false)
    private String name;
    private String description;
    private LocalDate updateDATE = LocalDate.now();

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public LocalDate getUpdateDATE() {
        return updateDATE;
    }

    public void setUpdateDATE(LocalDate updateDATE) {
        this.updateDATE = updateDATE;
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