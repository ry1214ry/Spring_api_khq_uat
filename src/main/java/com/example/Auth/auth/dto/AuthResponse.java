package com.example.Auth.auth.dto;

public record AuthResponse(
        String token,
        String tokenType,
        UserResponse user
) {
}
