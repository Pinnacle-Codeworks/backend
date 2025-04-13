package com.markguiang.backend.form.dto.form;

import jakarta.validation.constraints.NotNull;

public record FormFieldsDTO(@NotNull String name,
                            String description) {
}