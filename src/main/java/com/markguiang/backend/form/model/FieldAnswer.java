package com.markguiang.backend.form.model;

import com.markguiang.backend.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class FieldAnswer implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldAnswerId;

    // foreignKeys
    private Long formAnswersId;
    private Long fieldId;

    // fields
    private Double doubleAnswer;
    private String stringAnswer;
    private Integer integerAnswer;
    private LocalDate localDateAnswer;
    private Boolean booleanAnswer;

    // mappings

    public Long getFieldAnswerId() {
        return fieldAnswerId;
    }

    public void setFieldAnswerId(Long fieldAnswerId) {
        this.fieldAnswerId = fieldAnswerId;
    }

    public Long getFormAnswersId() {
        return formAnswersId;
    }

    public void setFormAnswersId(Long formAnswersId) {
        this.formAnswersId = formAnswersId;
    }

    public Double getDoubleAnswer() {
        return doubleAnswer;
    }

    public void setDoubleAnswer(Double doubleAnswer) {
        this.doubleAnswer = doubleAnswer;
    }

    public String getStringAnswer() {
        return stringAnswer;
    }

    public void setStringAnswer(String stringAnswer) {
        this.stringAnswer = stringAnswer;
    }

    public Integer getIntegerAnswer() {
        return integerAnswer;
    }

    public void setIntegerAnswer(Integer integerAnswer) {
        this.integerAnswer = integerAnswer;
    }

    public LocalDate getLocalDateAnswer() {
        return localDateAnswer;
    }

    public void setLocalDateAnswer(LocalDate localDateAnswer) {
        this.localDateAnswer = localDateAnswer;
    }

    public Boolean getBooleanAnswer() {
        return booleanAnswer;
    }

    public void setBooleanAnswer(Boolean booleanAnswer) {
        this.booleanAnswer = booleanAnswer;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public void clearIds() {
        fieldAnswerId = null;
        formAnswersId = null;
    }
}
