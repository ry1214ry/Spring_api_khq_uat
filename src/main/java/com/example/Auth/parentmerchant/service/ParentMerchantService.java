package com.example.Auth.parentmerchant.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;
import com.example.Auth.parentmerchant.dto.ParentMerchantRequest;
import com.example.Auth.parentmerchant.dto.ParentMerchantResponse;
import com.example.Auth.parentmerchant.model.ParentMerchant;
import com.example.Auth.parentmerchant.repository.ParentMerchantRepository;

@Service
public class ParentMerchantService {

    private final ParentMerchantRepository parentMerchantRepository;
    private final PasswordEncoder passwordEncoder;

    public ParentMerchantService(ParentMerchantRepository parentMerchantRepository, PasswordEncoder passwordEncoder) {
        this.parentMerchantRepository = parentMerchantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ParentMerchantResponse> findAll() {
        return parentMerchantRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ParentMerchantResponse findById(Integer id) {
        return toResponse(getParentMerchant(id));
    }

    @Transactional
    public ParentMerchantResponse create(ParentMerchantRequest request) {
        ParentMerchant merchant = new ParentMerchant(
                request.merchantId().trim(),
                passwordEncoder.encode(request.merchantPass().trim()),
                request.name().trim(),
                request.parentName().trim(),
                request.location().trim(),
                request.email().trim().toLowerCase(),
                request.mobileNumber().trim(),
                normalizeStatus(request.status()),
                normalizeSessions(request.sessions()),
                request.logTime(),
                trimToNull(request.image())
        );
        return toResponse(parentMerchantRepository.save(merchant));
    }

    @Transactional
    public ParentMerchantResponse update(Integer id, ParentMerchantRequest request) {
        ParentMerchant merchant = getParentMerchant(id);
        merchant.setMerchantId(request.merchantId().trim());
        merchant.setMerchantPass(passwordEncoder.encode(request.merchantPass().trim()));
        merchant.setName(request.name().trim());
        merchant.setParentName(request.parentName().trim());
        merchant.setLocation(request.location().trim());
        merchant.setEmail(request.email().trim().toLowerCase());
        merchant.setMobileNumber(request.mobileNumber().trim());
        merchant.setStatus(normalizeStatus(request.status()));
        merchant.setSessions(normalizeSessions(request.sessions()));
        merchant.setLogTime(request.logTime());
        merchant.setImage(trimToNull(request.image()));
        return toResponse(parentMerchantRepository.save(merchant));
    }

    @Transactional
    public MessageResponse delete(Integer id) {
        ParentMerchant merchant = getParentMerchant(id);
        parentMerchantRepository.delete(merchant);
        return new MessageResponse("Parent merchant deleted successfully");
    }

    private ParentMerchant getParentMerchant(Integer id) {
        return parentMerchantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent merchant not found with id: " + id));
    }

    private ParentMerchantResponse toResponse(ParentMerchant merchant) {
        return new ParentMerchantResponse(
                merchant.getId(),
                merchant.getMerchantId(),
                merchant.getName(),
                merchant.getParentName(),
                merchant.getLocation(),
                merchant.getEmail(),
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
