package com.example.Auth.checkout.dto;

public record CheckoutResponse(
        Integer id,
        String merchantId,
        String name,
        String location,
        String amt,
        String mdOrder,
        String qrCodeUrl,
        String currency,
        String mobileNumber,
        String checkoutDate,
        String returnUrl,
        String continueSuccessUrl,
        String session_token,
        String create_by,
        String continue_successful_url,
        String md_order,
        String mobile_number,
        String return_url,
        String image
) {
}
