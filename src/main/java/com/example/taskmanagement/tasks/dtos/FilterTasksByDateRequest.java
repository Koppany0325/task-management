package com.example.taskmanagement.tasks.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FilterTasksByDateRequest(
    @NotNull LocalDateTime date
) {
}
