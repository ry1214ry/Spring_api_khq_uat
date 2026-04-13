package com.example.Auth.role.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Auth.common.dto.MessageResponse;
import com.example.Auth.common.exception.ResourceNotFoundException;
import com.example.Auth.role.dto.RoleRequest;
import com.example.Auth.role.dto.RoleResponse;
import com.example.Auth.role.model.AppRole;
import com.example.Auth.role.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleResponse> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RoleResponse findById(Long id) {
        return toResponse(getRole(id));
    }

    @Transactional
    public RoleResponse create(RoleRequest request) {
        AppRole role = new AppRole(
                request.rolesId(),
                request.rolesType().trim(),
                request.status().trim().toLowerCase()
        );
        return toResponse(roleRepository.save(role));
    }

    @Transactional
    public RoleResponse update(Long id, RoleRequest request) {
        AppRole role = getRole(id);
        apply(role, request);
        return toResponse(roleRepository.save(role));
    }

    @Transactional
    public MessageResponse delete(Long id) {
        AppRole role = getRole(id);
        roleRepository.delete(role);
        return new MessageResponse("Role deleted successfully");
    }

    private AppRole getRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    private void apply(AppRole role, RoleRequest request) {
        role.setRolesId(request.rolesId());
        role.setRolesType(request.rolesType().trim());
        role.setStatus(request.status().trim().toLowerCase());
    }

    private RoleResponse toResponse(AppRole role) {
        return new RoleResponse(
                role.getId(),
                role.getRolesId(),
                role.getRolesType(),
                role.getStatus()
        );
    }
}
