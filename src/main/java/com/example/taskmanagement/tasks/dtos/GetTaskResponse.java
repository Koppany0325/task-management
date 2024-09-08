package com.example.taskmanagement.tasks.dtos;

import com.example.taskmanagement.tasks.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetTaskResponse(
    UUID id,
    String title,
    String description,
    LocalDateTime dueDate,
    TaskStatus status
) {
}
