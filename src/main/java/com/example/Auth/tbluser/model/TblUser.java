package com.example.Auth.tbluser.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_users")
public class TblUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", length = 255)
    private String username;

    @Column(name = "userpass", length = 255)
    private String userpass;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "role_type", length = 255)
    private String roleType;

    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "sessions", length = 255)
    private String sessions;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    protected TblUser() {
    }

    public TblUser(String username, String userpass, String description, String roleType, String status, String sessions,
            String image) {
        this.username = username;
        this.userpass = userpass;
        this.description = description;
        this.roleType = roleType;
        this.status = status;
        this.sessions = sessions;
        this.image = image;
    }

    @PrePersist
    void onCreate() {
        if (createDate == null) {
            createDate = LocalDateTime.now();
        }
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
