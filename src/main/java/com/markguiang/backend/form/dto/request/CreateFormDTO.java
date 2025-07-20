package com.markguiang.backend.form.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.base.annotation.ValidRequired;
import com.markguiang.backend.form.enum_.FieldType;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateFormDTO(
        @NotNull Long eventId,
        @NotNull String name,
        String description,
        @JsonProperty("fieldList")
        @ValidRequired
        List<CreateFormFieldDTO> createFormFieldDTOList
) {
    public record CreateFormFieldDTO(
            @NotNull String name,
            @NotNull FieldType fieldType,
            Boolean mandatory,
            @NotNull Integer order
    ) {}
}