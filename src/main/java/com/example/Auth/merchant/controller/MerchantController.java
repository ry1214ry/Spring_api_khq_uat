package com.example.Auth.merchant.controller;

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
import com.example.Auth.merchant.dto.MerchantRequest;
import com.example.Auth.merchant.dto.MerchantResponse;
import com.example.Auth.merchant.service.MerchantService;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity<List<MerchantResponse>> findAll() {
        return ResponseEntity.ok(merchantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(merchantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MerchantResponse> create(@Valid @RequestBody MerchantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(merchantService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantResponse> update(@PathVariable Integer id, @Valid @RequestBody MerchantRequest request) {
        return ResponseEntity.ok(merchantService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(merchantService.delete(id));
    }
}
