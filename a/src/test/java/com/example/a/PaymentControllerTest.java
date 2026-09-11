package com.example.a;

import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PaymentControllerTest {

  private final KafkaTemplate<String, PaymentEvent> kafkaTemplate = mock(KafkaTemplate.class);
  private final MockMvc mockMvc = MockMvcBuilders
      .standaloneSetup(new PaymentController(kafkaTemplate))
      .build();

    @Test
    void acceptsPaymentDocument() throws Exception {
        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "reference": "payment-123",
                                  "recipientId": "recipient-456",
                                  "currency": "EUR",
                                  "amount": 42.50
                                }
                                """))
                .andExpect(status().isAccepted());

                          verify(kafkaTemplate).send(
                            eq("payments"),
                            eq("payment-123"),
                            eq(new PaymentEvent("payment-123", "recipient-456", "EUR", new java.math.BigDecimal("42.50"))));
    }
}