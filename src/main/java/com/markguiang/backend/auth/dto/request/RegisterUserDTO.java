package com.markguiang.backend.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record RegisterUserDTO(
        @NotNull String username,
        @NotNull String password,
        @NotNull Long tenantId,
        String firstName,
        String lastName,
        String email) {
}
