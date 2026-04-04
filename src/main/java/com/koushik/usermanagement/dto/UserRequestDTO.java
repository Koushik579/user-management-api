package com.koushik.usermanagement.dto;

import jakarta.validation.constraints.*;

public record UserRequestDTO (
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotNull(message = "Age cannot be null")
        @Min(value = 18, message = "Must be greater than or equals to 18")
        Integer age,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8)
        String password,

        @NotBlank
        @Email(message = "Invalid Email")
        String email,

        @NotBlank
        String role
        ){}
