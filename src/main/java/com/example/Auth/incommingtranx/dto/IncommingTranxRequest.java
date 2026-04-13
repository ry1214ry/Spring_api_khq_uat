package com.example.Auth.incommingtranx.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncommingTranxRequest(
        @NotBlank(message = "mid is required")
        @Size(max = 100, message = "mid must not exceed 100 characters")
        String mid,
        @JsonAlias("src_account_id")
        @Size(max = 100, message = "srcAccountId must not exceed 100 characters")
        String srcAccountId,
        @Size(max = 300, message = "fccref must not exceed 300 characters")
        String fccref,
        @JsonAlias("merchant_id")
        @Size(max = 300, message = "merchantId must not exceed 300 characters")
        String merchantId,
        @Size(max = 300, message = "amount must not exceed 300 characters")
        String amount,
        @Size(max = 300, message = "currency must not exceed 300 characters")
        String currency,
        @JsonAlias("create_time")
        @Size(max = 100, message = "createTime must not exceed 100 characters")
        String createTime,
        @JsonAlias("trx_hash")
        @Size(max = 500, message = "trxHash must not exceed 500 characters")
        String trxHash,
        @JsonAlias("payment_type")
        @Size(max = 300, message = "paymentType must not exceed 300 characters")
        String paymentType,
        @JsonAlias("trn_type")
        @NotBlank(message = "trnType is required")
        @Size(max = 100, message = "trnType must not exceed 100 characters")
        String trnType,
        @JsonAlias("appr_code")
        @NotNull(message = "apprCode is required")
        @Size(max = 100, message = "apprCode must not exceed 100 characters")
        String apprCode,
        @Size(max = 300, message = "status must not exceed 300 characters")
        String status,
        @Size(max = 300, message = "storeName must not exceed 300 characters")
        String storeName,
        @Size(max = 255, message = "terminalLabel must not exceed 255 characters")
        String terminalLabel,
        @JsonAlias("customer_name")
        @Size(max = 300, message = "customerName must not exceed 300 characters")
        String customerName,
        @JsonAlias("trx_short_hash")
        @Size(max = 300, message = "trxShortHash must not exceed 300 characters")
        String trxShortHash,
        @Size(max = 300, message = "billNumber must not exceed 300 characters")
        String billNumber,
        @JsonAlias("create_date")
        @NotBlank(message = "createDate is required")
        @Size(max = 100, message = "createDate must not exceed 100 characters")
        String createDate,
        @JsonAlias("sale_draft_no")
        @NotBlank(message = "saleDraftNo is required")
        @Size(max = 250, message = "saleDraftNo must not exceed 250 characters")
        String saleDraftNo,
        @Size(max = 300, message = "bill_number must not exceed 300 characters")
        String bill_number,
        @Size(max = 300, message = "store_name must not exceed 300 characters")
        String store_name,
        @Size(max = 255, message = "terminal_label must not exceed 255 characters")
        String terminal_label,
        @Size(max = 255, message = "image must not exceed 255 characters")
        String image
) {
}
