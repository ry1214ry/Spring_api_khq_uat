package com.example.Auth.checkout.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.checkout.dto.CheckoutRequest;
import com.example.Auth.checkout.dto.CheckoutResponse;
import com.example.Auth.checkout.model.Checkout;
import com.example.Auth.checkout.repository.CheckoutRepository;
import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;

@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;

    public CheckoutService(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    public List<CheckoutResponse> findAll() {
        return checkoutRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CheckoutResponse findById(Integer id) {
        return toResponse(getCheckout(id));
    }

    @Transactional
    public CheckoutResponse create(CheckoutRequest request) {
        Checkout checkout = new Checkout(
                request.merchantId().trim(),
                request.name().trim(),
                request.location().trim(),
                request.amt().trim(),
                request.mdOrder().trim(),
                request.qrCodeUrl().trim(),
                request.currency().trim(),
                request.mobileNumber().trim(),
                request.checkoutDate().trim(),
                trimToEmpty(request.returnUrl()),
                trimToEmpty(request.continueSuccessUrl()),
                trimToEmpty(request.session_token()),
                trimToEmpty(request.create_by()),
                trimToEmpty(request.continue_successful_url()),
                trimToEmpty(request.md_order()),
                trimToEmpty(request.mobile_number()),
                trimToEmpty(request.return_url()),
                trimToNull(request.image())
        );
        return toResponse(checkoutRepository.save(checkout));
    }

    @Transactional
    public CheckoutResponse update(Integer id, CheckoutRequest request) {
        Checkout checkout = getCheckout(id);
        checkout.setMerchantId(request.merchantId().trim());
        checkout.setName(request.name().trim());
        checkout.setLocation(request.location().trim());
        checkout.setAmt(request.amt().trim());
        checkout.setMdOrder(request.mdOrder().trim());
        checkout.setQrCodeUrl(request.qrCodeUrl().trim());
        checkout.setCurrency(request.currency().trim());
        checkout.setMobileNumber(request.mobileNumber().trim());
        checkout.setCheckoutDate(request.checkoutDate().trim());
        checkout.setReturnUrl(trimToEmpty(request.returnUrl()));
        checkout.setContinueSuccessUrl(trimToEmpty(request.continueSuccessUrl()));
        checkout.setSessionToken(trimToEmpty(request.session_token()));
        checkout.setCreateBy(trimToEmpty(request.create_by()));
        checkout.setContinue_successful_url(trimToEmpty(request.continue_successful_url()));
        checkout.setMd_order(trimToEmpty(request.md_order()));
        checkout.setMobile_number(trimToEmpty(request.mobile_number()));
        checkout.setReturn_url(trimToEmpty(request.return_url()));
        checkout.setImage(trimToNull(request.image()));
        return toResponse(checkoutRepository.save(checkout));
    }

    @Transactional
    public MessageResponse delete(Integer id) {
        Checkout checkout = getCheckout(id);
        checkoutRepository.delete(checkout);
        return new MessageResponse("Checkout deleted successfully");
    }

    private Checkout getCheckout(Integer id) {
        return checkoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkout not found with id: " + id));
    }

    private CheckoutResponse toResponse(Checkout checkout) {
        return new CheckoutResponse(
                checkout.getId(),
                checkout.getMerchantId(),
                checkout.getName(),
                checkout.getLocation(),
                checkout.getAmt(),
                checkout.getMdOrder(),
                checkout.getQrCodeUrl(),
                checkout.getCurrency(),
                checkout.getMobileNumber(),
                checkout.getCheckoutDate(),
                checkout.getReturnUrl(),
                checkout.getContinueSuccessUrl(),
                checkout.getSessionToken(),
                checkout.getCreateBy(),
                checkout.getContinue_successful_url(),
                checkout.getMd_order(),
                checkout.getMobile_number(),
                checkout.getReturn_url(),
                checkout.getImage()
        );
    }

    private String trimToEmpty(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
