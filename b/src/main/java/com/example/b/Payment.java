package com.example.b;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("payments")
public record Payment(
        @Id UUID id,
        String reference,
        String recipientId,
        String currency,
        BigDecimal amount,
        Instant createdAt) implements Persistable<UUID> {

    public Payment(String reference, String recipientId, String currency, BigDecimal amount) {
        this(UUID.randomUUID(), reference, recipientId, currency, amount, Instant.now());
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}