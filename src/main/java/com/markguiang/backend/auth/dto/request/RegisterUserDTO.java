package com.markguiang.backend.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record RegisterUserDTO(
        @NotNull String username,
        @NotNull String password,
        String firstName,
        String lastName,
        String email
) {
}