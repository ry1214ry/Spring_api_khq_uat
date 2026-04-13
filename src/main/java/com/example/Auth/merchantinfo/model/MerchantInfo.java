package com.example.Auth.merchantinfo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_merchant_info")
public class MerchantInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "merchant_id", nullable = false, length = 100)
    private String merchantId;

    @Column(name = "`Parent_Merchant_Name`", nullable = false, length = 300)
    private String parentMerchantName;

    @Column(name = "sub_merchant_name", nullable = false, length = 300)
    private String subMerchantName;

    @Column(name = "`Contact_Person`", nullable = false, length = 100)
    private String contactPerson;

    @Column(name = "`Email`", nullable = false, length = 100)
    private String email;

    @Column(name = "`Website`", nullable = false, length = 100)
    private String website;

    @Column(name = "`Contact`", nullable = false, length = 100)
    private String contact;

    @Column(name = "`Business_License`", nullable = false, length = 100)
    private String businessLicense;

    @Column(name = "`Business_Type`", nullable = false, length = 100)
    private String businessType;

    @Column(name = "mcc_code", nullable = false, length = 50)
    private String mccCode;

    @Column(name = "`Transaction_Currency`", nullable = false, length = 100)
    private String transactionCurrency;

    @Column(name = "erwp", nullable = false, length = 100)
    private String erwp;

    @Column(name = "`Account_Number`", nullable = false, length = 100)
    private String accountNumber;

    @Column(name = "`QR`", length = 500)
    private String qr;

    @Column(name = "`tgID`", nullable = false, length = 500)
    private String tgId;

    @Column(name = "revers_code", nullable = false, length = 50)
    private String reversCode;

    @Column(name = "ewp", nullable = false, length = 100)
    private String ewp;

    @Column(name = "image", length = 255)
    private String image;

    protected MerchantInfo() {
    }

    public MerchantInfo(
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
        this.merchantId = merchantId;
        this.parentMerchantName = parentMerchantName;
        this.subMerchantName = subMerchantName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.website = website;
        this.contact = contact;
        this.businessLicense = businessLicense;
        this.businessType = businessType;
        this.mccCode = mccCode;
        this.transactionCurrency = transactionCurrency;
        this.erwp = erwp;
        this.accountNumber = accountNumber;
        this.qr = qr;
        this.tgId = tgId;
        this.reversCode = reversCode;
        this.ewp = ewp;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getParentMerchantName() {
        return parentMerchantName;
    }

    public void setParentMerchantName(String parentMerchantName) {
        this.parentMerchantName = parentMerchantName;
    }

    public String getSubMerchantName() {
        return subMerchantName;
    }

    public void setSubMerchantName(String subMerchantName) {
        this.subMerchantName = subMerchantName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMccCode() {
        return mccCode;
    }

    public void setMccCode(String mccCode) {
        this.mccCode = mccCode;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getErwp() {
        return erwp;
    }

    public void setErwp(String erwp) {
        this.erwp = erwp;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getTgId() {
        return tgId;
    }

    public void setTgId(String tgId) {
        this.tgId = tgId;
    }

    public String getReversCode() {
        return reversCode;
    }

    public void setReversCode(String reversCode) {
        this.reversCode = reversCode;
    }

    public String getEwp() {
        return ewp;
    }

    public void setEwp(String ewp) {
        this.ewp = ewp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
