package com.example.Auth.checkout.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckoutRequest(
        @JsonAlias("merchantID")
        @NotBlank(message = "merchantId is required")
        @Size(max = 100, message = "merchantId must not exceed 100 characters")
        String merchantId,
        @NotBlank(message = "name is required")
        @Size(max = 100, message = "name must not exceed 100 characters")
        String name,
        @NotBlank(message = "location is required")
        @Size(max = 100, message = "location must not exceed 100 characters")
        String location,
        @NotBlank(message = "amt is required")
        @Size(max = 50, message = "amt must not exceed 50 characters")
        String amt,
        @JsonAlias("mdOrder")
        @NotBlank(message = "mdOrder is required")
        @Size(max = 100, message = "mdOrder must not exceed 100 characters")
        String mdOrder,
        @JsonAlias("QRcode_url")
        @NotBlank(message = "qrCodeUrl is required")
        @Size(max = 1500, message = "qrCodeUrl must not exceed 1500 characters")
        String qrCodeUrl,
        @NotBlank(message = "currency is required")
        @Size(max = 100, message = "currency must not exceed 100 characters")
        String currency,
        @JsonAlias("mobileNumber")
        @NotBlank(message = "mobileNumber is required")
        @Size(max = 100, message = "mobileNumber must not exceed 100 characters")
        String mobileNumber,
        @JsonAlias("checkout_date")
        @NotBlank(message = "checkoutDate is required")
        @Size(max = 100, message = "checkoutDate must not exceed 100 characters")
        String checkoutDate,
        @JsonAlias("returnUrl")
        @Size(max = 1000, message = "returnUrl must not exceed 1000 characters")
        String returnUrl,
        @JsonAlias("continueSuccessUrl")
        @Size(max = 1000, message = "continueSuccessUrl must not exceed 1000 characters")
        String continueSuccessUrl,
        @JsonAlias("session_token")
        @Size(max = 1000, message = "session_token must not exceed 1000 characters")
        String session_token,
        @JsonAlias("create_by")
        @Size(max = 200, message = "create_by must not exceed 200 characters")
        String create_by,
        @Size(max = 1000, message = "continue_successful_url must not exceed 1000 characters")
        String continue_successful_url,
        @Size(max = 100, message = "md_order must not exceed 100 characters")
        String md_order,
        @Size(max = 100, message = "mobile_number must not exceed 100 characters")
        String mobile_number,
        @Size(max = 1000, message = "return_url must not exceed 1000 characters")
        String return_url,
        @Size(max = 255, message = "image must not exceed 255 characters")
        String image
) {
}
