package com.markguiang.backend.form.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"companyId", "eventId", "name"})
})
public class Form implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    // foreignKeys
    @Column(updatable = false)
    @NotNull
    private Long companyId;
    @NotNull
    @Column(updatable = false)
    private Long eventId;

    // fields
    @NotNull
    private String name;
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate updateDATE; //TODO initialize
    private Boolean deleted;

    // mappings
    @OneToMany(mappedBy = "formId", orphanRemoval = true)
    private List<Field> fieldList;
    @OneToMany(mappedBy = "formId", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    @Override
    public void clearIds() {
        formId = null;
        for (Field field: fieldList) {
            field.clearIds();
        }
    }
}