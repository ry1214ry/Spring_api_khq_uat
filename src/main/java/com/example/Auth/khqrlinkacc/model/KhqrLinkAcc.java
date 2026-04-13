package com.example.Auth.khqrlinkacc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_khqr_link_acc")
public class KhqrLinkAcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`merchantId`", nullable = false, length = 100)
    private String merchantId;

    @Column(name = "account", nullable = false, length = 300)
    private String account;

    @Column(name = "ccy", nullable = false, length = 100)
    private String ccy;

    @Column(name = "`merchantName`", nullable = false, length = 300)
    private String merchantName;

    @Column(name = "`merchantBranch`", nullable = false, length = 300)
    private String merchantBranch;

    @Column(name = "merchant_branch", nullable = false, length = 300)
    private String merchant_branch;

    @Column(name = "merchant_id", nullable = false, length = 100)
    private String merchant_id;

    @Column(name = "merchant_name", nullable = false, length = 300)
    private String merchant_name;

    @Column(name = "image", length = 255)
    private String image;

    protected KhqrLinkAcc() {
    }

    public KhqrLinkAcc(
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
        this.merchantId = merchantId;
        this.account = account;
        this.ccy = ccy;
        this.merchantName = merchantName;
        this.merchantBranch = merchantBranch;
        this.merchant_branch = merchant_branch;
        this.merchant_id = merchant_id;
        this.merchant_name = merchant_name;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantBranch() {
        return merchantBranch;
    }

    public void setMerchantBranch(String merchantBranch) {
        this.merchantBranch = merchantBranch;
    }

    public String getMerchant_branch() {
        return merchant_branch;
    }

    public void setMerchant_branch(String merchant_branch) {
        this.merchant_branch = merchant_branch;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
