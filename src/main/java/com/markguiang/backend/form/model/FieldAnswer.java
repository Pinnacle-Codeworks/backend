package com.markguiang.backend.form.model;

import com.markguiang.backend.base.model.AbstractBaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class FieldAnswer extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long fieldAnswerId;

    // foreignKeys
    @Column(updatable = false, nullable = false)
    private Long formAnswersId;

    @Column(updatable = false, nullable = false)
    private Long fieldId;

    // fields
    private Double doubleAnswer;
    private String stringAnswer;
    private Integer integerAnswer;
    private LocalDate localDateAnswer;
    private Boolean booleanAnswer;

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
}