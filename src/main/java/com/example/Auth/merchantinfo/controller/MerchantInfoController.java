package com.example.Auth.merchantinfo.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.merchantinfo.dto.MerchantInfoRequest;
import com.example.Auth.merchantinfo.dto.MerchantInfoResponse;
import com.example.Auth.merchantinfo.service.MerchantInfoService;


@RestController
@RequestMapping("/api/merchant-info")
public class MerchantInfoController {

    private final MerchantInfoService merchantInfoService;

    public MerchantInfoController(MerchantInfoService merchantInfoService) {
        this.merchantInfoService = merchantInfoService;
    }

    @GetMapping
    public ResponseEntity<List<MerchantInfoResponse>> findAll() {
        return ResponseEntity.ok(merchantInfoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantInfoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(merchantInfoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MerchantInfoResponse> create(@Valid @RequestBody MerchantInfoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(merchantInfoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantInfoResponse> update(@PathVariable Integer id, @Valid @RequestBody MerchantInfoRequest request) {
        return ResponseEntity.ok(merchantInfoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(merchantInfoService.delete(id));
    }
}
