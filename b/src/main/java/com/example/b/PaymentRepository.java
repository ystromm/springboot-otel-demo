package com.example.b;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, UUID> {
}