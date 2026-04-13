package com.example.Auth.parentmerchant.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ParentMerchantRequest(
        @JsonAlias("merchant_id")
        @NotBlank(message = "merchantId is required")
        @Size(max = 50, message = "merchantId must not exceed 50 characters")
        String merchantId,
        @JsonAlias("merchant_pass")
        @NotBlank(message = "merchantPass is required")
        @Size(max = 255, message = "merchantPass must not exceed 255 characters")
        String merchantPass,
        @NotBlank(message = "name is required")
        @Size(max = 100, message = "name must not exceed 100 characters")
        String name,
        @JsonAlias("parent_name")
        @NotBlank(message = "parentName is required")
        @Size(max = 300, message = "parentName must not exceed 300 characters")
        String parentName,
        @NotBlank(message = "location is required")
        @Size(max = 500, message = "location must not exceed 500 characters")
        String location,
        @NotBlank(message = "email is required")
        @Email(message = "email format is invalid")
        @Size(max = 300, message = "email must not exceed 300 characters")
        String email,
        @JsonAlias("mobile_number")
        @NotBlank(message = "mobileNumber is required")
        @Size(max = 50, message = "mobileNumber must not exceed 50 characters")
        String mobileNumber,
        @NotBlank(message = "status is required")
        @Pattern(
                regexp = "enable|disable",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "status must be either enable or disable"
        )
        String status,
        @NotBlank(message = "sessions is required")
        @Pattern(
                regexp = "active|inactive",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "sessions must be either active or inactive"
        )
        String sessions,
        @JsonAlias("log_time")
        @NotNull(message = "logTime is required")
        @PositiveOrZero(message = "logTime must be 0 or greater")
        Integer logTime,
        @Size(max = 255, message = "image must not exceed 255 characters")
        String image
) {
}
