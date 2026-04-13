package com.example.Auth.role.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_roles")
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roles_id", nullable = false)
    private Integer rolesId;

    @Column(name = "roles_type", nullable = false, length = 100)
    private String rolesType;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    protected AppRole() {
    }

    public AppRole(Integer rolesId, String rolesType, String status) {
        this.rolesId = rolesId;
        this.rolesType = rolesType;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Integer getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public String getRolesType() {
        return rolesType;
    }

    public void setRolesType(String rolesType) {
        this.rolesType = rolesType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
