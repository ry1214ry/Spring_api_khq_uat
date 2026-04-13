package com.example.Auth.parentmerchant.dto;

public record ParentMerchantResponse(
        Integer id,
        String merchantId,
        String name,
        String parentName,
        String location,
        String email,
        String mobileNumber,
        String status,
        String sessions,
        Integer logTime,
        String image,
        String createDate
) {
}
