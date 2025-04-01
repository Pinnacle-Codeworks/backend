package com.markguiang.backend.form.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.base.BaseEntity;
import com.markguiang.backend.form.enum_.FieldType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Field implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldId;

    // foreignKeys
    @NotNull
    @Column(updatable = false)
    private Long formId;

    // fields
    @NotNull
    private String name;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(updatable = false)
    private FieldType fieldType;
    private Boolean mandatory = false;
    @NotNull
    @Column(name = "order_")
    private Integer order;
    private Boolean deleted = false;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void clearIds() {
        fieldId = null;
        formId = null;
    }
}