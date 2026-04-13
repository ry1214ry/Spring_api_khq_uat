package com.example.Auth.merchantinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Auth.merchantinfo.model.MerchantInfo;

public interface MerchantInfoRepository extends JpaRepository<MerchantInfo, Integer> {
}
