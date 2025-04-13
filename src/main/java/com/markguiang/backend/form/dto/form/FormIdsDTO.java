package com.markguiang.backend.form.dto.form;

import jakarta.validation.constraints.NotNull;

public record FormIdsDTO(@NotNull Long formId,
                         @NotNull Long companyId,
                         @NotNull Long eventId) {
}