package com.example.taskmanagement.security.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
    @NotBlank String username,
    @NotBlank String password
) {
}
