package com.example.Auth.merchantinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;
import com.example.Auth.merchantinfo.dto.MerchantInfoRequest;
import com.example.Auth.merchantinfo.dto.MerchantInfoResponse;
import com.example.Auth.merchantinfo.model.MerchantInfo;
import com.example.Auth.merchantinfo.repository.MerchantInfoRepository;

@Service
public class MerchantInfoService {

    private final MerchantInfoRepository merchantInfoRepository;

    public MerchantInfoService(MerchantInfoRepository merchantInfoRepository) {
        this.merchantInfoRepository = merchantInfoRepository;
    }

    public List<MerchantInfoResponse> findAll() {
        return merchantInfoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MerchantInfoResponse findById(Integer id) {
        return toResponse(getMerchantInfo(id));
    }

    @Transactional
    public MerchantInfoResponse create(MerchantInfoRequest request) {
        MerchantInfo merchantInfo = new MerchantInfo(
                request.merchantId().trim(),
                request.parentMerchantName().trim(),
                request.subMerchantName().trim(),
                request.contactPerson().trim(),
                request.email().trim().toLowerCase(),
                request.website().trim(),
                request.contact().trim(),
                request.businessLicense().trim(),
                request.businessType().trim(),
                request.mccCode().trim(),
                request.transactionCurrency().trim(),
                request.erwp().trim(),
                request.accountNumber().trim(),
                trimToNull(request.qr()),
                request.tgId().trim(),
                request.reversCode().trim(),
                request.ewp().trim(),
                trimToNull(request.image())
        );
        return toResponse(merchantInfoRepository.save(merchantInfo));
    }

    @Transactional
    public MerchantInfoResponse update(Integer id, MerchantInfoRequest request) {
        MerchantInfo merchantInfo = getMerchantInfo(id);
        merchantInfo.setMerchantId(request.merchantId().trim());
        merchantInfo.setParentMerchantName(request.parentMerchantName().trim());
        merchantInfo.setSubMerchantName(request.subMerchantName().trim());
        merchantInfo.setContactPerson(request.contactPerson().trim());
        merchantInfo.setEmail(request.email().trim().toLowerCase());
        merchantInfo.setWebsite(request.website().trim());
        merchantInfo.setContact(request.contact().trim());
        merchantInfo.setBusinessLicense(request.businessLicense().trim());
        merchantInfo.setBusinessType(request.businessType().trim());
        merchantInfo.setMccCode(request.mccCode().trim());
        merchantInfo.setTransactionCurrency(request.transactionCurrency().trim());
        merchantInfo.setErwp(request.erwp().trim());
        merchantInfo.setAccountNumber(request.accountNumber().trim());
        merchantInfo.setQr(trimToNull(request.qr()));
        merchantInfo.setTgId(request.tgId().trim());
        merchantInfo.setReversCode(request.reversCode().trim());
        merchantInfo.setEwp(request.ewp().trim());
        merchantInfo.setImage(trimToNull(request.image()));
        return toResponse(merchantInfoRepository.save(merchantInfo));
    }

    @Transactional
    public MessageResponse delete(Integer id) {
        MerchantInfo merchantInfo = getMerchantInfo(id);
        merchantInfoRepository.delete(merchantInfo);
        return new MessageResponse("Merchant info deleted successfully");
    }

    private MerchantInfo getMerchantInfo(Integer id) {
        return merchantInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant info not found with id: " + id));
    }

    private MerchantInfoResponse toResponse(MerchantInfo merchantInfo) {
        return new MerchantInfoResponse(
                merchantInfo.getId(),
                merchantInfo.getMerchantId(),
                merchantInfo.getParentMerchantName(),
                merchantInfo.getSubMerchantName(),
                merchantInfo.getContactPerson(),
                merchantInfo.getEmail(),
                merchantInfo.getWebsite(),
                merchantInfo.getContact(),
                merchantInfo.getBusinessLicense(),
                merchantInfo.getBusinessType(),
                merchantInfo.getMccCode(),
                merchantInfo.getTransactionCurrency(),
                merchantInfo.getErwp(),
                merchantInfo.getAccountNumber(),
                merchantInfo.getQr(),
                merchantInfo.getTgId(),
                merchantInfo.getReversCode(),
                merchantInfo.getEwp(),
                merchantInfo.getImage()
        );
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
