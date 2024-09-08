package com.example.taskmanagement.tasks.dtos;

import com.example.taskmanagement.tasks.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateTaskRequest {
    @NotBlank private String title;
    private String description;
    @NotNull private LocalDateTime dueDate;
    @NotNull private TaskStatus status;
}
