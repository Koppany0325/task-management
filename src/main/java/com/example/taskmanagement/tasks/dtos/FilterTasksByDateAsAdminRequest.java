package com.example.taskmanagement.tasks.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FilterTasksByDateAsAdminRequest(
    @NotNull LocalDateTime date,
    Integer id
) {
}
