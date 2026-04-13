package com.example.Auth.merchant.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;
import com.example.Auth.merchant.dto.MerchantRequest;
import com.example.Auth.merchant.dto.MerchantResponse;
import com.example.Auth.merchant.model.Merchant;
import com.example.Auth.merchant.repository.MerchantRepository;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;

    public MerchantService(MerchantRepository merchantRepository, PasswordEncoder passwordEncoder) {
        this.merchantRepository = merchantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<MerchantResponse> findAll() {
        return merchantRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MerchantResponse findById(Integer id) {
        return toResponse(getMerchant(id));
    }

    @Transactional
    public MerchantResponse create(MerchantRequest request) {
        Merchant merchant = new Merchant(
                request.merchantId().trim(),
                passwordEncoder.encode(request.merchantPass().trim()),
                request.username().trim(),
                request.parentMerchantName().trim(),
                request.location().trim(),
                request.mobileNumber().trim(),
                normalizeStatus(request.status()),
                normalizeSessions(request.sessions()),
                request.logTime(),
                trimToNull(request.image())
        );
        return toResponse(merchantRepository.save(merchant));
    }

    @Transactional
    public MerchantResponse update(Integer id, MerchantRequest request) {
        Merchant merchant = getMerchant(id);
        merchant.setMerchantId(request.merchantId().trim());
        merchant.setMerchantPass(passwordEncoder.encode(request.merchantPass().trim()));
        merchant.setUsername(request.username().trim());
        merchant.setParentMerchantName(request.parentMerchantName().trim());
        merchant.setLocation(request.location().trim());
        merchant.setMobileNumber(request.mobileNumber().trim());
        merchant.setStatus(normalizeStatus(request.status()));
        merchant.setSessions(normalizeSessions(request.sessions()));
        merchant.setLogTime(request.logTime());
        merchant.setImage(trimToNull(request.image()));
        return toResponse(merchantRepository.save(merchant));
    }

    @Transactional
    public MessageResponse delete(Integer id) {
        Merchant merchant = getMerchant(id);
        merchantRepository.delete(merchant);
        return new MessageResponse("Merchant deleted successfully");
    }

    private Merchant getMerchant(Integer id) {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + id));
    }

    private MerchantResponse toResponse(Merchant merchant) {
        return new MerchantResponse(
                merchant.getId(),
                merchant.getMerchantId(),
                merchant.getUsername(),
                merchant.getParentMerchantName(),
                merchant.getLocation(),
                merchant.getMobileNumber(),
                merchant.getStatus(),
                merchant.getSessions(),
                merchant.getLogTime(),
                merchant.getImage(),
                merchant.getCreateDate() != null ? merchant.getCreateDate().toString() : null
        );
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizeStatus(String status) {
        String normalized = trimToNull(status);
        return normalized == null ? null : normalized.toLowerCase();
    }

    private String normalizeSessions(String sessions) {
        String normalized = trimToNull(sessions);
        return normalized == null ? null : normalized.toLowerCase();
    }
}
