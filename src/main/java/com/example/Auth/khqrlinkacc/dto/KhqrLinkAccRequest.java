package com.example.Auth.khqrlinkacc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record KhqrLinkAccRequest(
        @NotBlank(message = "merchantId is required")
        @Size(max = 100, message = "merchantId must not exceed 100 characters")
        String merchantId,
        @NotBlank(message = "account is required")
        @Size(max = 300, message = "account must not exceed 300 characters")
        String account,
        @NotBlank(message = "ccy is required")
        @Size(max = 100, message = "ccy must not exceed 100 characters")
        String ccy,
        @NotBlank(message = "merchantName is required")
        @Size(max = 300, message = "merchantName must not exceed 300 characters")
        String merchantName,
        @NotBlank(message = "merchantBranch is required")
        @Size(max = 300, message = "merchantBranch must not exceed 300 characters")
        String merchantBranch,
        @Size(max = 300, message = "merchant_branch must not exceed 300 characters")
        String merchant_branch,
        @Size(max = 100, message = "merchant_id must not exceed 100 characters")
        String merchant_id,
        @Size(max = 300, message = "merchant_name must not exceed 300 characters")
        String merchant_name,
        @Size(max = 255, message = "image must not exceed 255 characters")
        String image
) {
}
