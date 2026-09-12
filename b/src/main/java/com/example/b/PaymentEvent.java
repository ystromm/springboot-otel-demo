package com.example.b;

import java.math.BigDecimal;

public record PaymentEvent(
        String reference,
        String recipientId,
        String currency,
        BigDecimal amount) {
}
