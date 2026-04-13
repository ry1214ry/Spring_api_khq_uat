package com.example.Auth.checkout.controller;

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

import com.example.Auth.checkout.dto.CheckoutRequest;
import com.example.Auth.checkout.dto.CheckoutResponse;
import com.example.Auth.checkout.service.CheckoutService;
import com.example.Auth.common.dto.MessageResponse;

@RestController
@RequestMapping("/api/checkouts")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping
    public ResponseEntity<List<CheckoutResponse>> findAll() {
        return ResponseEntity.ok(checkoutService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckoutResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(checkoutService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> create(@Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckoutResponse> update(@PathVariable Integer id, @Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(checkoutService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(checkoutService.delete(id));
    }
}
