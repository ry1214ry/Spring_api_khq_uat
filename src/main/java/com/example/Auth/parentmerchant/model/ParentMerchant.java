package com.example.Auth.parentmerchant.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_all_parent_merchant")
public class ParentMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`merchantId`", nullable = false, length = 50)
    private String merchantId;

    @Column(name = "merchant_pass", nullable = false, length = 255)
    private String merchantPass;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "parent_name", nullable = false, length = 300)
    private String parentName;

    @Column(name = "location", nullable = false, length = 500)
    private String location;

    @Column(name = "email", nullable = false, length = 300)
    private String email;

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

    @Column(name = "merchant_id", nullable = false, length = 50)
    private String merchantIdCopy;

    @Column(name = "mobile_number", nullable = false, length = 50)
    private String mobileNumberCopy;

    @Column(name = "image", length = 255)
    private String image;

    protected ParentMerchant() {
    }

    public ParentMerchant(
            String merchantId,
            String merchantPass,
            String name,
            String parentName,
            String location,
            String email,
            String mobileNumber,
            String status,
            String sessions,
            Integer logTime,
            String image
    ) {
        this.merchantId = merchantId;
        this.merchantPass = merchantPass;
        this.name = name;
        this.parentName = parentName;
        this.location = location;
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
