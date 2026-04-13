package com.example.Auth.tbluser.controller;

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
import com.example.Auth.tbluser.dto.TblUserRequest;
import com.example.Auth.tbluser.dto.TblUserResponse;
import com.example.Auth.tbluser.service.TblUserService;

@RestController
@RequestMapping("/api/users")
public class TblUserController {

    private final TblUserService tblUserService;

    public TblUserController(TblUserService tblUserService) {
        this.tblUserService = tblUserService;
    }

    @GetMapping
    public ResponseEntity<List<TblUserResponse>> findAll() {
        return ResponseEntity.ok(tblUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TblUserResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(tblUserService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TblUserResponse> create(@Valid @RequestBody TblUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tblUserService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TblUserResponse> update(@PathVariable Integer id, @Valid @RequestBody TblUserRequest request) {
        return ResponseEntity.ok(tblUserService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(tblUserService.delete(id));
    }
}
