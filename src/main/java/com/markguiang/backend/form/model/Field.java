package com.markguiang.backend.form.model;

import com.markguiang.backend.form.enum_.FieldType;
import jakarta.persistence.*;

@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long fieldId;

    // foreignKeys
    @ManyToOne
    @JoinColumn(updatable = false, nullable = false)
    private Form form;

    // fields
    @Column(updatable = false, nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = false)
    private FieldType fieldType;
    private Boolean mandatory = false;
    @Column(name = "order_", nullable = false)
    private Integer order;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
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
}