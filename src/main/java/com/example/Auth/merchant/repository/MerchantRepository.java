package com.example.Auth.merchant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Auth.merchant.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
}
