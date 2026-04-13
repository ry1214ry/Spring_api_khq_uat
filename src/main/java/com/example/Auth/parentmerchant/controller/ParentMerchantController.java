package com.example.Auth.parentmerchant.controller;

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
import com.example.Auth.parentmerchant.dto.ParentMerchantRequest;
import com.example.Auth.parentmerchant.dto.ParentMerchantResponse;
import com.example.Auth.parentmerchant.service.ParentMerchantService;

@RestController
@RequestMapping("/api/parent-merchants")
public class ParentMerchantController {

    private final ParentMerchantService parentMerchantService;

    public ParentMerchantController(ParentMerchantService parentMerchantService) {
        this.parentMerchantService = parentMerchantService;
    }

    @GetMapping
    public ResponseEntity<List<ParentMerchantResponse>> findAll() {
        return ResponseEntity.ok(parentMerchantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentMerchantResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(parentMerchantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParentMerchantResponse> create(@Valid @RequestBody ParentMerchantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parentMerchantService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentMerchantResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody ParentMerchantRequest request
    ) {
        return ResponseEntity.ok(parentMerchantService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(parentMerchantService.delete(id));
    }
}
