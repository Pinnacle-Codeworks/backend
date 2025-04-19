package com.markguiang.backend.form.model;

import com.markguiang.backend.base.AbstractBaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "formId" }) })
public class FormAnswers extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formAnswersId;

    // foreignKeys
    private Long formId;
    private Long userId;

    // mappings
    @Transient
    private List<FieldAnswer> fieldAnswerList;

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
}
