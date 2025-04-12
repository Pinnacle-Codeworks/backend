package com.markguiang.backend.auth.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserIdsDTO(@NotNull String userId,
                         @NotNull String companyId) {
}