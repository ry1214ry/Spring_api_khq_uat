package com.example.Auth.merchant.dto;

public record MerchantResponse(
        Integer id,
        String merchantId,
        String username,
        String parentMerchantName,
        String location,
        String mobileNumber,
        String status,
        String sessions,
        Integer logTime,
        String image,
        String createDate
) {
}
