package com.markguiang.backend.form.dto.field;

import com.markguiang.backend.form.enum_.FieldType;
import jakarta.validation.constraints.NotNull;

public record FieldFieldsDTO(@NotNull String name,
                             @NotNull FieldType fieldType,
                             Boolean mandatory,
                             @NotNull Integer order) {
}