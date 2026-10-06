package com.example.b;

import java.util.List;
import io.opentelemetry.api.trace.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

@Component
public class PaymentEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventListener.class);
    private final PaymentRepository paymentRepository;

    public PaymentEventListener(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    static final List<String> CURRENCIES = List.of("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "SEK");

    @KafkaListener(topics = "payments", groupId = "payment-processors")
    public void handle(ConsumerRecord<String, PaymentEvent> event) {
        final var eventValue = event.value();
        Span.current().setAttribute("payment.reference", eventValue.reference());
        final var currency = eventValue.currency();
        logger.info("Received payment event: reference={}, recipientId={}, currency={}, amount={}",
                eventValue.reference(), eventValue.recipientId(), currency, eventValue.amount());
        
        CURRENCIES.stream()
                .filter(c -> c.equalsIgnoreCase(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported currency: " + currency));

        Payment payment = paymentRepository.save(new Payment(
                eventValue.reference(),
                eventValue.recipientId(),
                currency,
                eventValue.amount()));
    }
}
