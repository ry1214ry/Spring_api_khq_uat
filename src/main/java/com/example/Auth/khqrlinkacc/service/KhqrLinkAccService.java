package com.example.Auth.khqrlinkacc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;
import com.example.Auth.khqrlinkacc.dto.KhqrLinkAccRequest;
import com.example.Auth.khqrlinkacc.dto.KhqrLinkAccResponse;
import com.example.Auth.khqrlinkacc.model.KhqrLinkAcc;
import com.example.Auth.khqrlinkacc.repository.KhqrLinkAccRepository;

@Service
public class KhqrLinkAccService {

    private final KhqrLinkAccRepository khqrLinkAccRepository;

    public KhqrLinkAccService(KhqrLinkAccRepository khqrLinkAccRepository) {
        this.khqrLinkAccRepository = khqrLinkAccRepository;
    }

    public List<KhqrLinkAccResponse> findAll() {
        return khqrLinkAccRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public KhqrLinkAccResponse findById(Integer id) {
        return toResponse(getKhqrLinkAcc(id));
    }

    @Transactional
    public KhqrLinkAccResponse create(KhqrLinkAccRequest request) {
        KhqrLinkAcc khqrLinkAcc = new KhqrLinkAcc(
                request.merchantId().trim(),
                request.account().trim(),
                request.ccy().trim(),
                request.merchantName().trim(),
                request.merchantBranch().trim(),
                trimToEmpty(request.merchant_branch()),
                trimToEmpty(request.merchant_id()),
                trimToEmpty(request.merchant_name()),
                trimToNull(request.image())
        );
        return toResponse(khqrLinkAccRepository.save(khqrLinkAcc));
    }

    @Transactional
    public KhqrLinkAccResponse update(Integer id, KhqrLinkAccRequest request) {
        KhqrLinkAcc khqrLinkAcc = getKhqrLinkAcc(id);
        khqrLinkAcc.setMerchantId(request.merchantId().trim());
        khqrLinkAcc.setAccount(request.account().trim());
        khqrLinkAcc.setCcy(request.ccy().trim());
        khqrLinkAcc.setMerchantName(request.merchantName().trim());
        khqrLinkAcc.setMerchantBranch(request.merchantBranch().trim());
        khqrLinkAcc.setMerchant_branch(trimToEmpty(request.merchant_branch()));
        khqrLinkAcc.setMerchant_id(trimToEmpty(request.merchant_id()));
        khqrLinkAcc.setMerchant_name(trimToEmpty(request.merchant_name()));
        khqrLinkAcc.setImage(trimToNull(request.image()));
        return toResponse(khqrLinkAccRepository.save(khqrLinkAcc));
    }

    @Transactional
    public MessageResponse delete(Integer id) {
        KhqrLinkAcc khqrLinkAcc = getKhqrLinkAcc(id);
        khqrLinkAccRepository.delete(khqrLinkAcc);
        return new MessageResponse("KHQR link account deleted successfully");
    }

    private KhqrLinkAcc getKhqrLinkAcc(Integer id) {
        return khqrLinkAccRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KHQR link account not found with id: " + id));
    }

    private KhqrLinkAccResponse toResponse(KhqrLinkAcc khqrLinkAcc) {
        return new KhqrLinkAccResponse(
                khqrLinkAcc.getId(),
                khqrLinkAcc.getMerchantId(),
                khqrLinkAcc.getAccount(),
                khqrLinkAcc.getCcy(),
                khqrLinkAcc.getMerchantName(),
                khqrLinkAcc.getMerchantBranch(),
                khqrLinkAcc.getMerchant_branch(),
                khqrLinkAcc.getMerchant_id(),
                khqrLinkAcc.getMerchant_name(),
                khqrLinkAcc.getImage()
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
