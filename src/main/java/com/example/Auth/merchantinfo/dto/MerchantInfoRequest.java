package com.example.Auth.merchantinfo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchantInfoRequest(
        @JsonAlias("merchant_id")
        @NotBlank(message = "merchantId is required")
        @Size(max = 100, message = "merchantId must not exceed 100 characters")
        String merchantId,
        @JsonAlias({"Parent_Merchant_Name", "parent_merchant_name"})
        @NotBlank(message = "parentMerchantName is required")
        @Size(max = 300, message = "parentMerchantName must not exceed 300 characters")
        String parentMerchantName,
        @JsonAlias("sub_merchant_name")
        @NotBlank(message = "subMerchantName is required")
        @Size(max = 300, message = "subMerchantName must not exceed 300 characters")
        String subMerchantName,
        @JsonAlias({"Contact_Person", "contact_person"})
        @NotBlank(message = "contactPerson is required")
        @Size(max = 100, message = "contactPerson must not exceed 100 characters")
        String contactPerson,
        @JsonAlias("Email")
        @NotBlank(message = "email is required")
        @Email(message = "email format is invalid")
        @Size(max = 100, message = "email must not exceed 100 characters")
        String email,
        @JsonAlias("Website")
        @NotBlank(message = "website is required")
        @Size(max = 100, message = "website must not exceed 100 characters")
        String website,
        @JsonAlias("Contact")
        @NotBlank(message = "contact is required")
        @Size(max = 100, message = "contact must not exceed 100 characters")
        String contact,
        @JsonAlias({"Business_License", "business_license"})
        @NotBlank(message = "businessLicense is required")
        @Size(max = 100, message = "businessLicense must not exceed 100 characters")
        String businessLicense,
        @JsonAlias({"Business_Type", "business_type"})
        @NotBlank(message = "businessType is required")
        @Size(max = 100, message = "businessType must not exceed 100 characters")
        String businessType,
        @JsonAlias("mcc_code")
        @NotBlank(message = "mccCode is required")
        @Size(max = 50, message = "mccCode must not exceed 50 characters")
        String mccCode,
        @JsonAlias({"Transaction_Currency", "transaction_currency"})
        @NotBlank(message = "transactionCurrency is required")
        @Size(max = 100, message = "transactionCurrency must not exceed 100 characters")
        String transactionCurrency,
        @NotBlank(message = "erwp is required")
        @Size(max = 100, message = "erwp must not exceed 100 characters")
        String erwp,
        @JsonAlias({"Account_Number", "account_number"})
        @NotBlank(message = "accountNumber is required")
        @Size(max = 100, message = "accountNumber must not exceed 100 characters")
        String accountNumber,
        @JsonAlias({"QR", "qr"})
        @Size(max = 500, message = "qr must not exceed 500 characters")
        String qr,
        @JsonAlias({"tgID", "tg_id"})
        @NotBlank(message = "tgId is required")
        @Size(max = 500, message = "tgId must not exceed 500 characters")
        String tgId,
        @JsonAlias("revers_code")
        @NotBlank(message = "reversCode is required")
        @Size(max = 50, message = "reversCode must not exceed 50 characters")
        String reversCode,
        @NotNull(message = "ewp is required")
        @Size(max = 100, message = "ewp must not exceed 100 characters")
        String ewp,
        @Size(max = 255, message = "image must not exceed 255 characters")
        String image
) {
}
