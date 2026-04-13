package com.example.Auth.auth.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        String username,
        String role,
        boolean enabled
) {
}
