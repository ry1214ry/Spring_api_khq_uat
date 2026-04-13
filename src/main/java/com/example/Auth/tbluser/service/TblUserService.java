package com.example.Auth.tbluser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;
import com.example.Auth.tbluser.dto.TblUserRequest;
import com.example.Auth.tbluser.dto.TblUserResponse;
import com.example.Auth.tbluser.model.TblUser;
import com.example.Auth.tbluser.repository.TblUserRepository;

@Service
public class TblUserService {

    private final TblUserRepository tblUserRepository;
    private final PasswordEncoder passwordEncoder;

    public TblUserService(TblUserRepository tblUserRepository, PasswordEncoder passwordEncoder) {
        this.tblUserRepository = tblUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<TblUserResponse> findAll() {
        return tblUserRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TblUserResponse findById(Integer id) {
        return toResponse(getUser(id));
    }

    @Transactional
    public TblUserResponse create(TblUserRequest request) {
        TblUser user = new TblUser(
                request.username().trim(),
                passwordEncoder.encode(request.userpass().trim()),
                trimToNull(request.description()),
                trimToNull(request.roleType()),
                normalizeStatus(request.status()),
                normalizeSessions(request.sessions()),
                trimToNull(request.image())
        );
        return toResponse(tblUserRepository.save(user));
    }

    @Transactional
    public TblUserResponse update(Integer id, TblUserRequest request) {
        TblUser user = getUser(id);
        user.setUsername(request.username().trim());
        user.setUserpass(passwordEncoder.encode(request.userpass().trim()));
        user.setDescription(trimToNull(request.description()));
        user.setRoleType(trimToNull(request.roleType()));
        user.setStatus(normalizeStatus(request.status()));
        user.setSessions(normalizeSessions(request.sessions()));
        user.setImage(trimToNull(request.image()));
        return toResponse(tblUserRepository.save(user));
    }

    @Transactional
    public MessageResponse delete(Integer id) {
        TblUser user = getUser(id);
        tblUserRepository.delete(user);
        return new MessageResponse("User deleted successfully");
    }

    private TblUser getUser(Integer id) {
        return tblUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private TblUserResponse toResponse(TblUser user) {
        return new TblUserResponse(
                user.getId(),
                user.getUsername(),
                user.getDescription(),
                user.getRoleType(),
                user.getStatus(),
                user.getSessions(),
                user.getImage(),
                user.getCreateDate() != null ? user.getCreateDate().toString() : null
        );
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizeStatus(String status) {
        String normalized = trimToNull(status);
        return normalized == null ? null : normalized.toLowerCase();
    }

    private String normalizeSessions(String sessions) {
        String normalized = trimToNull(sessions);
        return normalized == null ? null : normalized.toLowerCase();
    }
}
