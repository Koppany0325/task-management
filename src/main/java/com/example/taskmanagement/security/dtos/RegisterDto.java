package com.example.taskmanagement.security.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
    @NotBlank String username,
    @NotBlank String password
) {
}
