package com.example.Auth.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Auth.checkout.model.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
}
