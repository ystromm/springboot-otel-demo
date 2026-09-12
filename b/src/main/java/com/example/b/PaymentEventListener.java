package com.example.b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventListener.class);
    private final PaymentRepository paymentRepository;

    public PaymentEventListener(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(topics = "payments", groupId = "payment-processors")
    public void handle(PaymentEvent event) {
        Payment payment = paymentRepository.save(new Payment(
                event.reference(),
                event.recipientId(),
                event.currency(),
                event.amount()));
        logger.info("Received payment event: reference={}, recipientId={}, currency={}, amount={}",
                event.reference(), event.recipientId(), event.currency(), event.amount());
    }
}
