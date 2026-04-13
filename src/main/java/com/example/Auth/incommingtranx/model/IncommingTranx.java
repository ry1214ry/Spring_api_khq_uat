package com.example.Auth.incommingtranx.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_incomming_tranx")
public class IncommingTranx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mid", nullable = false, length = 100)
    private String mid;

    @Column(name = "src_account_id", length = 100)
    private String srcAccountId;

    @Column(name = "fccref", length = 300)
    private String fccref;

    @Column(name = "merchant_id", length = 300)
    private String merchantId;

    @Column(name = "amount", length = 300)
    private String amount;

    @Column(name = "currency", length = 300)
    private String currency;

    @Column(name = "create_time", length = 100)
    private String createTime;

    @Column(name = "trx_hash", length = 500)
    private String trxHash;

    @Column(name = "payment_type", length = 300)
    private String paymentType;

    @Column(name = "trn_type", nullable = false, length = 100)
    private String trnType;

    @Column(name = "appr_code", nullable = false, length = 100)
    private String apprCode;

    @Column(name = "status", length = 300)
    private String status;

    @Column(name = "`storeName`", length = 300)
    private String storeName;

    @Column(name = "`terminalLabel`", length = 255)
    private String terminalLabel;

    @Column(name = "customer_name", length = 300)
    private String customerName;

    @Column(name = "trx_short_hash", length = 300)
    private String trxShortHash;

    @Column(name = "`billNumber`", length = 300)
    private String billNumber;

    @Column(name = "create_date", nullable = false, length = 100)
    private String createDate;

    @Column(name = "sale_draft_no", nullable = false, length = 250)
    private String saleDraftNo;

    @Column(name = "bill_number", length = 300)
    private String bill_number;

    @Column(name = "store_name", length = 300)
    private String store_name;

    @Column(name = "terminal_label", length = 255)
    private String terminal_label;

    @Column(name = "image", length = 255)
    private String image;

    protected IncommingTranx() {
    }

    public IncommingTranx(
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
        this.mid = mid;
        this.srcAccountId = srcAccountId;
        this.fccref = fccref;
        this.merchantId = merchantId;
        this.amount = amount;
        this.currency = currency;
        this.createTime = createTime;
        this.trxHash = trxHash;
        this.paymentType = paymentType;
        this.trnType = trnType;
        this.apprCode = apprCode;
        this.status = status;
        this.storeName = storeName;
        this.terminalLabel = terminalLabel;
        this.customerName = customerName;
        this.trxShortHash = trxShortHash;
        this.billNumber = billNumber;
        this.createDate = createDate;
        this.saleDraftNo = saleDraftNo;
        this.bill_number = bill_number;
        this.store_name = store_name;
        this.terminal_label = terminal_label;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSrcAccountId() {
        return srcAccountId;
    }

    public void setSrcAccountId(String srcAccountId) {
        this.srcAccountId = srcAccountId;
    }

    public String getFccref() {
        return fccref;
    }

    public void setFccref(String fccref) {
        this.fccref = fccref;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTrxHash() {
        return trxHash;
    }

    public void setTrxHash(String trxHash) {
        this.trxHash = trxHash;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTrnType() {
        return trnType;
    }

    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public String getApprCode() {
        return apprCode;
    }

    public void setApprCode(String apprCode) {
        this.apprCode = apprCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTerminalLabel() {
        return terminalLabel;
    }

    public void setTerminalLabel(String terminalLabel) {
        this.terminalLabel = terminalLabel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTrxShortHash() {
        return trxShortHash;
    }

    public void setTrxShortHash(String trxShortHash) {
        this.trxShortHash = trxShortHash;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSaleDraftNo() {
        return saleDraftNo;
    }

    public void setSaleDraftNo(String saleDraftNo) {
        this.saleDraftNo = saleDraftNo;
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String bill_number) {
        this.bill_number = bill_number;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getTerminal_label() {
        return terminal_label;
    }

    public void setTerminal_label(String terminal_label) {
        this.terminal_label = terminal_label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
