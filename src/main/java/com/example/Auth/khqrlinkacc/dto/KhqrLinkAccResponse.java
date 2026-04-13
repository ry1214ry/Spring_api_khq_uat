package com.example.Auth.khqrlinkacc.dto;

public record KhqrLinkAccResponse(
        Integer id,
        String merchantId,
        String account,
        String ccy,
        String merchantName,
        String merchantBranch,
        String merchant_branch,
        String merchant_id,
        String merchant_name,
        String image
) {
}
