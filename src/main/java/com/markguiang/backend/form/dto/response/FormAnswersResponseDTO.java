package com.markguiang.backend.form.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class FormAnswersResponseDTO {
    private Long formAnswersId;
    private Long formId;
    @JsonProperty("fieldAnswerList")
    private List<FieldAnswerResponseDTO> fieldAnswerResponseDTOList;

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

    public List<FieldAnswerResponseDTO> getFieldAnswerResponseDTOList() {
        return fieldAnswerResponseDTOList;
    }

    public void setFieldAnswerResponseDTOList(List<FieldAnswerResponseDTO> fieldAnswerResponseDTOList) {
        this.fieldAnswerResponseDTOList = fieldAnswerResponseDTOList;
    }

    public static class FieldAnswerResponseDTO {
        @NotNull
        private Long fieldAnswerId;
        @NotNull
        private Long fieldId;
        private Double doubleAnswer;
        private String stringAnswer;
        private Integer integerAnswer;
        private LocalDate localDateAnswer;
        private Boolean booleanAnswer;

        public Boolean getBooleanAnswer() {
            return booleanAnswer;
        }

        public void setBooleanAnswer(Boolean booleanAnswer) {
            this.booleanAnswer = booleanAnswer;
        }

        public LocalDate getLocalDateAnswer() {
            return localDateAnswer;
        }

        public void setLocalDateAnswer(LocalDate localDateAnswer) {
            this.localDateAnswer = localDateAnswer;
        }

        public Integer getIntegerAnswer() {
            return integerAnswer;
        }

        public void setIntegerAnswer(Integer integerAnswer) {
            this.integerAnswer = integerAnswer;
        }

        public String getStringAnswer() {
            return stringAnswer;
        }

        public void setStringAnswer(String stringAnswer) {
            this.stringAnswer = stringAnswer;
        }

        public Double getDoubleAnswer() {
            return doubleAnswer;
        }

        public void setDoubleAnswer(Double doubleAnswer) {
            this.doubleAnswer = doubleAnswer;
        }

        public Long getFieldId() {
            return fieldId;
        }

        public void setFieldId(Long fieldId) {
            this.fieldId = fieldId;
        }

        public Long getFieldAnswerId() {
            return fieldAnswerId;
        }

        public void setFieldAnswerId(Long fieldAnswerId) {
            this.fieldAnswerId = fieldAnswerId;
        }
    }
}