package com.example.Auth.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Auth.role.model.AppRole;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
}
