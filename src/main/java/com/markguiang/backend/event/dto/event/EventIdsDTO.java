package com.markguiang.backend.event.dto.event;

import jakarta.validation.constraints.NotNull;

public record EventIdsDTO(@NotNull Long eventId,
                          Long companyId) {
}