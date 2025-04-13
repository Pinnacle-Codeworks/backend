package com.markguiang.backend.form.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.annotation.ValidRequired;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AnswerFormDTO(
        @NotNull Long formId,
        @JsonProperty("fieldAnswerList")
        @ValidRequired
        List<FieldAnswerDTO> fieldAnswerDTOList
) {
    public record FieldAnswerDTO(
            @NotNull Long fieldId,
            Double doubleAnswer,
            String stringAnswer,
            Integer integerAnswer,
            LocalDate localDateAnswer,
            Boolean booleanAnswer
    ) {}
}