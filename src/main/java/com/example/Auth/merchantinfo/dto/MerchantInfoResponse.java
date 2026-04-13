package com.example.Auth.merchantinfo.dto;

public record MerchantInfoResponse(
        Integer id,
        String merchantId,
        String parentMerchantName,
        String subMerchantName,
        String contactPerson,
        String email,
        String website,
        String contact,
        String businessLicense,
        String businessType,
        String mccCode,
        String transactionCurrency,
        String erwp,
        String accountNumber,
        String qr,
        String tgId,
        String reversCode,
        String ewp,
        String image
) {
}
