package com.example.Auth.merchant.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_all_merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`merchantId`", nullable = false, length = 100)
    private String merchantId;

    @Column(name = "merchant_pass", nullable = false, length = 255)
    private String merchantPass;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "parent_merchant_name", nullable = false, length = 300)
    private String parentMerchantName;

    @Column(name = "location", nullable = false, length = 500)
    private String location;

    @Column(name = "`mobileNumber`", nullable = false, length = 50)
    private String mobileNumber;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "sessions", nullable = false, length = 20)
    private String sessions;

    @Column(name = "log_time", nullable = false)
    private Integer logTime;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "merchant_id", nullable = false, length = 100)
    private String merchantIdCopy;

    @Column(name = "mobile_number", nullable = false, length = 50)
    private String mobileNumberCopy;

    @Column(name = "image", length = 255)
    private String image;

    protected Merchant() {
    }

    public Merchant(
            String merchantId,
            String merchantPass,
            String username,
            String parentMerchantName,
            String location,
            String mobileNumber,
            String status,
            String sessions,
            Integer logTime,
            String image
    ) {
        this.merchantId = merchantId;
        this.merchantPass = merchantPass;
        this.username = username;
        this.parentMerchantName = parentMerchantName;
        this.location = location;
        this.mobileNumber = mobileNumber;
        this.status = status;
        this.sessions = sessions;
        this.logTime = logTime;
        this.image = image;
        syncDuplicateColumns();
    }

    @PrePersist
    void onCreate() {
        if (createDate == null) {
            createDate = LocalDateTime.now();
        }
        syncDuplicateColumns();
    }

    public Integer getId() {
        return id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        this.merchantIdCopy = merchantId;
    }

    public String getMerchantPass() {
        return merchantPass;
    }

    public void setMerchantPass(String merchantPass) {
        this.merchantPass = merchantPass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParentMerchantName() {
        return parentMerchantName;
    }

    public void setParentMerchantName(String parentMerchantName) {
        this.parentMerchantName = parentMerchantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        this.mobileNumberCopy = mobileNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public Integer getLogTime() {
        return logTime;
    }

    public void setLogTime(Integer logTime) {
        this.logTime = logTime;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getMerchantIdCopy() {
        return merchantIdCopy;
    }

    public String getMobileNumberCopy() {
        return mobileNumberCopy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private void syncDuplicateColumns() {
        this.merchantIdCopy = merchantId;
        this.mobileNumberCopy = mobileNumber;
    }
}
