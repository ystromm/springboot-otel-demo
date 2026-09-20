package com.example.b;

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

    @KafkaListener(topics = "payments", groupId = "payment-processors")
    public void handle(ConsumerRecord<String, PaymentEvent> event) {
        var paymentEvent = event.value();
        Payment payment = paymentRepository.save(new Payment(
                paymentEvent.reference(),
                paymentEvent.recipientId(),
                paymentEvent.currency(),
                paymentEvent.amount()));
        logger.info("Received payment event: reference={}, recipientId={}, currency={}, amount={}",
                paymentEvent.reference(), paymentEvent.recipientId(), paymentEvent.currency(), paymentEvent.amount());
    }
}
