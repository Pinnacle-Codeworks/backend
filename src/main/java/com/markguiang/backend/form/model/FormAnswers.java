package com.markguiang.backend.form.model;

import com.markguiang.backend.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"userId", "formId"})
})
public class FormAnswers implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formAnswersId;

    // foreignKeys
    private Long formId;
    private Long userId;

    // mappings
    @OneToMany
    @JoinColumn(name = "formAnswersId")
    private List<FieldAnswer> fieldAnswerList;

    // fields
    private LocalDate updateDATE;

    public Long getFormAnswersId() {
        return formAnswersId;
    }

    public void setFormAnswersId(Long formAnswersId) {
        this.formAnswersId = formAnswersId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<FieldAnswer> getFieldAnswerList() {
        return fieldAnswerList;
    }

    public void setFieldAnswerList(List<FieldAnswer> fieldAnswerList) {
        this.fieldAnswerList = fieldAnswerList;
    }

    public LocalDate getUpdateDATE() {
        return updateDATE;
    }

    public void setUpdateDATE(LocalDate updateDATE) {
        this.updateDATE = updateDATE;
    }

    @Override
    public void clearIds() {
        formAnswersId = null;
        userId = null;
        for (FieldAnswer fieldAnswer: fieldAnswerList) {
            fieldAnswer.clearIds();
        }
    }
}