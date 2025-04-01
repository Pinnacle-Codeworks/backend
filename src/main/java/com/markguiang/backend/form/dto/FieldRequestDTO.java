package com.markguiang.backend.form.dto;

import com.markguiang.backend.form.enum_.FieldType;
import com.markguiang.backend.form.model.Field;

public class FieldRequestDTO {
    private Long fieldId;
    private String name;
    private FieldType fieldType;
    private Boolean mandatory;
    private Integer order;
    private Boolean deleted;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
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

    public static Field toField(FieldRequestDTO fieldRequestDTO) {
        Field field = new Field();
        field.setName(fieldRequestDTO.getName());
        field.setFieldType(fieldRequestDTO.getFieldType());
        field.setMandatory(fieldRequestDTO.getMandatory());
        field.setOrder(fieldRequestDTO.getOrder());
        field.setDeleted(fieldRequestDTO.getDeleted());
        return field;
    }
}
