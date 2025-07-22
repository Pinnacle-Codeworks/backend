package com.markguiang.backend.event.domain.adapters.http.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateEventDTO(@NotNull UUID id, String description, String location) {}
