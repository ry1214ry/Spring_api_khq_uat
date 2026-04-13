package com.example.Auth.incommingtranx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;
import com.example.Auth.incommingtranx.dto.IncommingTranxRequest;
import com.example.Auth.incommingtranx.dto.IncommingTranxResponse;
import com.example.Auth.incommingtranx.model.IncommingTranx;
import com.example.Auth.incommingtranx.repository.IncommingTranxRepository;

@Service
public class IncommingTranxService {

    private final IncommingTranxRepository incommingTranxRepository;

    public IncommingTranxService(IncommingTranxRepository incommingTranxRepository) {
        this.incommingTranxRepository = incommingTranxRepository;
    }

    public List<IncommingTranxResponse> findAll() {
        return incommingTranxRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public IncommingTranxResponse findById(Integer id) {
        return toResponse(getIncommingTranx(id));
    }

    @Transactional
    public IncommingTranxResponse create(IncommingTranxRequest request) {
        IncommingTranx incommingTranx = new IncommingTranx(
                request.mid().trim(),
                trimToNull(request.srcAccountId()),
                trimToNull(request.fccref()),
                trimToNull(request.merchantId()),
                trimToNull(request.amount()),
                trimToNull(request.currency()),
                trimToNull(request.createTime()),
                trimToNull(request.trxHash()),
                trimToNull(request.paymentType()),
                request.trnType().trim(),
                request.apprCode().trim(),
                trimToNull(request.status()),
                trimToNull(request.storeName()),
                trimToNull(request.terminalLabel()),
                trimToNull(request.customerName()),
                trimToNull(request.trxShortHash()),
                trimToNull(request.billNumber()),
                request.createDate().trim(),
                request.saleDraftNo().trim(),
                trimToNull(request.bill_number()),
                trimToNull(request.store_name()),
                trimToNull(request.terminal_label()),
                trimToNull(request.image())
        );
        return toResponse(incommingTranxRepository.save(incommingTranx));
    }

    @Transactional
    public IncommingTranxResponse update(Integer id, IncommingTranxRequest request) {
        IncommingTranx incommingTranx = getIncommingTranx(id);
        incommingTranx.setMid(request.mid().trim());
        incommingTranx.setSrcAccountId(trimToNull(request.srcAccountId()));
        incommingTranx.setFccref(trimToNull(request.fccref()));
        incommingTranx.setMerchantId(trimToNull(request.merchantId()));
        incommingTranx.setAmount(trimToNull(request.amount()));
        incommingTranx.setCurrency(trimToNull(request.currency()));
        incommingTranx.setCreateTime(trimToNull(request.createTime()));
        incommingTranx.setTrxHash(trimToNull(request.trxHash()));
        incommingTranx.setPaymentType(trimToNull(request.paymentType()));
        incommingTranx.setTrnType(request.trnType().trim());
        incommingTranx.setApprCode(request.apprCode().trim());
        incommingTranx.setStatus(trimToNull(request.status()));
        incommingTranx.setStoreName(trimToNull(request.storeName()));
        incommingTranx.setTerminalLabel(trimToNull(request.terminalLabel()));
        incommingTranx.setCustomerName(trimToNull(request.customerName()));
        incommingTranx.setTrxShortHash(trimToNull(request.trxShortHash()));
        incommingTranx.setBillNumber(trimToNull(request.billNumber()));
        incommingTranx.setCreateDate(request.createDate().trim());
        incommingTranx.setSaleDraftNo(request.saleDraftNo().trim());
        incommingTranx.setBill_number(trimToNull(request.bill_number()));
        incommingTranx.setStore_name(trimToNull(request.store_name()));
        incommingTranx.setTerminal_label(trimToNull(request.terminal_label()));
        incommingTranx.setImage(trimToNull(request.image()));
        return toResponse(incommingTranxRepository.save(incommingTranx));
    }

    @Transactional
    public MessageResponse delete(Integer id) {
        IncommingTranx incommingTranx = getIncommingTranx(id);
        incommingTranxRepository.delete(incommingTranx);
        return new MessageResponse("Incoming transaction deleted successfully");
    }

    private IncommingTranx getIncommingTranx(Integer id) {
        return incommingTranxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incoming transaction not found with id: " + id));
    }

    private IncommingTranxResponse toResponse(IncommingTranx incommingTranx) {
        return new IncommingTranxResponse(
                incommingTranx.getId(),
                incommingTranx.getMid(),
                incommingTranx.getSrcAccountId(),
                incommingTranx.getFccref(),
                incommingTranx.getMerchantId(),
                incommingTranx.getAmount(),
                incommingTranx.getCurrency(),
                incommingTranx.getCreateTime(),
                incommingTranx.getTrxHash(),
                incommingTranx.getPaymentType(),
                incommingTranx.getTrnType(),
                incommingTranx.getApprCode(),
                incommingTranx.getStatus(),
                incommingTranx.getStoreName(),
                incommingTranx.getTerminalLabel(),
                incommingTranx.getCustomerName(),
                incommingTranx.getTrxShortHash(),
                incommingTranx.getBillNumber(),
                incommingTranx.getCreateDate(),
                incommingTranx.getSaleDraftNo(),
                incommingTranx.getBill_number(),
                incommingTranx.getStore_name(),
                incommingTranx.getTerminal_label(),
                incommingTranx.getImage()
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
