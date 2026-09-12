package com.example.b;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("payments")
public record Payment(
        @Id UUID id,
        String reference,
        String recipientId,
        String currency,
        BigDecimal amount,
        Instant createdAt) {

    public Payment(String reference, String recipientId, String currency, BigDecimal amount) {
        this(UUID.randomUUID(), reference, recipientId, currency, amount, Instant.now());
    }
}