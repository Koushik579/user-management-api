package com.koushik.usermanagement.dto;

public record UserResponseDTO(
        Long id,
        String name,
        int age,
        String email,
        String role
) {}