package com.example.Auth.common.dto;

public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message
) {
}
