package com.example.Auth.token.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Auth.token.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query("select t from Token t where t.user.id = :userId and t.expired = false and t.revoked = false")
    List<Token> findAllValidTokensByUserId(Long userId);
}
