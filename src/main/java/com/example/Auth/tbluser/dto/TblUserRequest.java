package com.example.Auth.tbluser.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TblUserRequest(
        @NotBlank(message = "username is required")
        @Size(max = 255, message = "username must not exceed 255 characters")
        String username,
        @NotBlank(message = "userpass is required")
        @Size(max = 255, message = "userpass must not exceed 255 characters")
        String userpass,
        @Size(max = 255, message = "description must not exceed 255 characters")
        String description,
        @JsonAlias("role_type")
        @Size(max = 255, message = "roleType must not exceed 255 characters")
        String roleType,
        @Pattern(
                regexp = "enable|disable",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "status must be either enable or disable"
        )
        String status,
        @Pattern(
                regexp = "active|inactive",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "sessions must be either active or inactive"
        )
        String sessions,
        @Size(max = 255, message = "image must not exceed 255 characters")
        String image
) {
}
