package com.example.taskmanagement.tasks.dtos;

import com.example.taskmanagement.tasks.enums.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.time.LocalDateTime;

public record UpdateTaskRequest(
    @NotEmpty String title,
    String description,
    @NotNull LocalDateTime dueDate,
    @NonNull TaskStatus status
) {
}
