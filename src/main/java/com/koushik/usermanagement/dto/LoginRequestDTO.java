package com.koushik.usermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank
        @Email(message = "Invalid Email")
        String email,
        @NotBlank String password) {

}
