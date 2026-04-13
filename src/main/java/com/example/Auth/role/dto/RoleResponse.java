package com.example.Auth.role.dto;

public record RoleResponse(
        Long id,
        Integer rolesId,
        String rolesType,
        String status
) {
}
