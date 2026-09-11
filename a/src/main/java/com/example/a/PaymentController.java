package com.example.a;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Payments", description = "Payment ingestion endpoints")
public class PaymentController {

    private static final String PAYMENTS_TOPIC = "payments";

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentController(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(path = "/payment", consumes = MediaType.APPLICATION_JSON_VALUE)
        @Operation(
            summary = "Accept a payment",
            description = "Validates a payment and publishes it as an event to the payments Kafka topic.",
            responses = {
                @ApiResponse(responseCode = "202", description = "Payment accepted for processing"),
                @ApiResponse(responseCode = "400", description = "Payment document is invalid", content = @Content)
            })
    public ResponseEntity<Void> createPayment(@Valid @RequestBody PaymentRequest payment) {
        PaymentEvent event = new PaymentEvent(
                payment.reference(),
                payment.recipientId(),
                payment.currency(),
                payment.amount());
        kafkaTemplate.send(PAYMENTS_TOPIC, event.reference(), event);
        return ResponseEntity.accepted().build();
    }
}