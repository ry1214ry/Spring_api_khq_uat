package com.example.Auth.checkout.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_checkout")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`merchantID`", nullable = false, length = 100)
    private String merchantId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "amt", nullable = false, length = 50)
    private String amt;

    @Column(name = "`mdOrder`", nullable = false, length = 100)
    private String mdOrder;

    @Column(name = "`QRcode_url`", nullable = false, length = 1500)
    private String qrCodeUrl;

    @Column(name = "currency", nullable = false, length = 100)
    private String currency;

    @Column(name = "`mobileNumber`", nullable = false, length = 100)
    private String mobileNumber;

    @Column(name = "checkout_date", nullable = false, length = 100)
    private String checkoutDate;

    @Column(name = "`returnUrl`", nullable = false, length = 1000)
    private String returnUrl;

    @Column(name = "`continueSuccessUrl`", nullable = false, length = 1000)
    private String continueSuccessUrl;

    @Column(name = "session_token", nullable = false, length = 1000)
    private String sessionToken;

    @Column(name = "create_by", nullable = false, length = 200)
    private String createBy;

    @Column(name = "continue_successful_url", nullable = false, length = 1000)
    private String continue_successful_url;

    @Column(name = "md_order", nullable = false, length = 100)
    private String md_order;

    @Column(name = "mobile_number", nullable = false, length = 100)
    private String mobile_number;

    @Column(name = "return_url", nullable = false, length = 1000)
    private String return_url;

    @Column(name = "image", length = 255)
    private String image;

    protected Checkout() {
    }

    public Checkout(
            String merchantId,
            String name,
            String location,
            String amt,
            String mdOrder,
            String qrCodeUrl,
            String currency,
            String mobileNumber,
            String checkoutDate,
            String returnUrl,
            String continueSuccessUrl,
            String sessionToken,
            String createBy,
            String continue_successful_url,
            String md_order,
            String mobile_number,
            String return_url,
            String image
    ) {
        this.merchantId = merchantId;
        this.name = name;
        this.location = location;
        this.amt = amt;
        this.mdOrder = mdOrder;
        this.qrCodeUrl = qrCodeUrl;
        this.currency = currency;
        this.mobileNumber = mobileNumber;
        this.checkoutDate = checkoutDate;
        this.returnUrl = returnUrl;
        this.continueSuccessUrl = continueSuccessUrl;
        this.sessionToken = sessionToken;
        this.createBy = createBy;
        this.continue_successful_url = continue_successful_url;
        this.md_order = md_order;
        this.mobile_number = mobile_number;
        this.return_url = return_url;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getMdOrder() {
        return mdOrder;
    }

    public void setMdOrder(String mdOrder) {
        this.mdOrder = mdOrder;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getContinueSuccessUrl() {
        return continueSuccessUrl;
    }

    public void setContinueSuccessUrl(String continueSuccessUrl) {
        this.continueSuccessUrl = continueSuccessUrl;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getContinue_successful_url() {
        return continue_successful_url;
    }

    public void setContinue_successful_url(String continue_successful_url) {
        this.continue_successful_url = continue_successful_url;
    }

    public String getMd_order() {
        return md_order;
    }

    public void setMd_order(String md_order) {
        this.md_order = md_order;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
