package com.example.b;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentEventListener.class);

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public Page<Payment> listPayments(@RequestParam(defaultValue = "0") int page) {
        final PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        final Page<Payment> payments = paymentRepository.findAll(pageRequest);
        logger.info("Retrieved {} payments for page {}", payments.getNumberOfElements(), page);
        return payments;
    }

    @PostMapping("/generate")
    public List<Payment> generatePayments() {
        List<Payment> payments = java.util.stream.Stream.generate(this::randomPayment)
                .limit(10)
                .toList();
        return StreamSupport.stream(paymentRepository.saveAll(payments).spliterator(), false).toList();
    }

    private Payment randomPayment() {
        String reference = "PAY-" + UUID.randomUUID();
        String recipientId = "recipient-" + UUID.randomUUID();
        String[] currencies = {"EUR", "USD", "GBP", "SEK"};
        String currency = currencies[ThreadLocalRandom.current().nextInt(currencies.length)];
        BigDecimal amount = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(100, 100_000), 2);
        return new Payment(reference, recipientId, currency, amount);
    }
}