package com.markguiang.backend.auth.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserFieldsWithoutPasswordDTO(
                            String firstName,
                            String lastName,
                            @NotNull String username,
                            String email) {
}