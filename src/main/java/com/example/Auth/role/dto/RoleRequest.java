package com.example.Auth.role.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RoleRequest(
        @JsonAlias("roles_id")
        @NotNull(message = "rolesId is required")
        @Positive(message = "rolesId must be greater than 0")
        Integer rolesId,
        @JsonAlias("roles_type")
        @NotBlank(message = "rolesType is required")
        @Size(max = 100, message = "rolesType must not exceed 100 characters")
        String rolesType,
        @NotBlank(message = "status is required")
        @Pattern(
                regexp = "enable|disable",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "status must be either enable or disable"
        )
        String status
) {
}
