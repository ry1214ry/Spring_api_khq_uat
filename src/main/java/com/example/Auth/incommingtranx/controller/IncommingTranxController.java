package com.example.Auth.incommingtranx.controller;

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
import com.example.Auth.incommingtranx.dto.IncommingTranxRequest;
import com.example.Auth.incommingtranx.dto.IncommingTranxResponse;
import com.example.Auth.incommingtranx.service.IncommingTranxService;

@RestController
@RequestMapping("/api/incomming-tranx")
public class IncommingTranxController {

    private final IncommingTranxService incommingTranxService;

    public IncommingTranxController(IncommingTranxService incommingTranxService) {
        this.incommingTranxService = incommingTranxService;
    }

    @GetMapping
    public ResponseEntity<List<IncommingTranxResponse>> findAll() {
        return ResponseEntity.ok(incommingTranxService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncommingTranxResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(incommingTranxService.findById(id));
    }

    @PostMapping
    public ResponseEntity<IncommingTranxResponse> create(@Valid @RequestBody IncommingTranxRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(incommingTranxService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncommingTranxResponse> update(
            @PathVariable Integer id,
            @Valid @RequestBody IncommingTranxRequest request
    ) {
        return ResponseEntity.ok(incommingTranxService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(incommingTranxService.delete(id));
    }
}
