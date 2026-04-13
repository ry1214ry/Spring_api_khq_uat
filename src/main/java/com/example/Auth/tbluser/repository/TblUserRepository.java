package com.example.Auth.tbluser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Auth.tbluser.model.TblUser;

public interface TblUserRepository extends JpaRepository<TblUser, Integer> {
}
