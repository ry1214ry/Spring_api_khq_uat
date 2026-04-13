package com.example.Auth.incommingtranx.dto;

public record IncommingTranxResponse(
        Integer id,
        String mid,
        String srcAccountId,
        String fccref,
        String merchantId,
        String amount,
        String currency,
        String createTime,
        String trxHash,
        String paymentType,
        String trnType,
        String apprCode,
        String status,
        String storeName,
        String terminalLabel,
        String customerName,
        String trxShortHash,
        String billNumber,
        String createDate,
        String saleDraftNo,
        String bill_number,
        String store_name,
        String terminal_label,
        String image
) {
}
