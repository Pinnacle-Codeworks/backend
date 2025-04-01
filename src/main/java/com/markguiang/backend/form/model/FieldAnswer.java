package com.markguiang.backend.form.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class FieldAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldAnswerId;

    // foreignKeys
    private Long formAnswersId;

    // fields
    private Double doubleAnswer;
    private String stringAnswer;
    private Integer integerAnswer;
    private LocalDate localDateAnswer;
    private Boolean booleanAnswer;

    // mappings
    @ManyToOne
    private Field field;

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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
