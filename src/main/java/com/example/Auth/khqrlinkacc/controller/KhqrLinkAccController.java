package com.example.Auth.khqrlinkacc.controller;

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
import com.example.Auth.khqrlinkacc.dto.KhqrLinkAccRequest;
import com.example.Auth.khqrlinkacc.dto.KhqrLinkAccResponse;
import com.example.Auth.khqrlinkacc.service.KhqrLinkAccService;

@RestController
@RequestMapping("/api/khqr-link-accounts")
public class KhqrLinkAccController {

    private final KhqrLinkAccService khqrLinkAccService;

    public KhqrLinkAccController(KhqrLinkAccService khqrLinkAccService) {
        this.khqrLinkAccService = khqrLinkAccService;
    }

    @GetMapping
    public ResponseEntity<List<KhqrLinkAccResponse>> findAll() {
        return ResponseEntity.ok(khqrLinkAccService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhqrLinkAccResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(khqrLinkAccService.findById(id));
    }

    @PostMapping
    public ResponseEntity<KhqrLinkAccResponse> create(@Valid @RequestBody KhqrLinkAccRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(khqrLinkAccService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhqrLinkAccResponse> update(@PathVariable Integer id, @Valid @RequestBody KhqrLinkAccRequest request) {
        return ResponseEntity.ok(khqrLinkAccService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(khqrLinkAccService.delete(id));
    }
}
