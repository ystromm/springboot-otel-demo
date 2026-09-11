package com.example.a;

import java.math.BigDecimal;

public record PaymentEvent(
        String reference,
        String recipientId,
        String currency,
        BigDecimal amount) {
}