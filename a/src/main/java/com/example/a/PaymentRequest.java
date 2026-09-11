package com.example.a;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PaymentRequest(
        @Schema(description = "Unique payment reference", example = "payment-123")
        @NotBlank String reference,
        @Schema(description = "Identifier of the payment recipient", example = "recipient-456")
        @NotBlank String recipientId,
        @Schema(description = "Three-letter ISO currency code", example = "EUR", pattern = "[A-Za-z]{3}")
        @NotBlank @Pattern(regexp = "[A-Za-z]{3}") String currency,
        @Schema(description = "Payment amount", example = "42.50", minimum = "0.01")
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount) {
}