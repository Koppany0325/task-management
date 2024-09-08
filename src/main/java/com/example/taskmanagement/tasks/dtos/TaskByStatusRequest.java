package com.example.taskmanagement.tasks.dtos;

import com.example.taskmanagement.tasks.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;


public record TaskByStatusRequest(
    Integer id,
    @NotNull TaskStatus status
) {
}
