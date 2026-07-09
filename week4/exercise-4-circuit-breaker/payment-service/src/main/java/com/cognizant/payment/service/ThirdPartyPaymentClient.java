package com.cognizant.payment.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Simulates a slow / unreliable third-party payment gateway.
 * ~60% of calls fail or hang beyond the circuit breaker's timeout.
 */
@Service
public class ThirdPartyPaymentClient {

    private final Random random = new Random();

    public String processPayment(String paymentId, double amount) {
        int scenario = random.nextInt(10);

        if (scenario < 3) {
            // Simulate a slow call
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            throw new RuntimeException("Third-party gateway timed out");
        }
        if (scenario < 6) {
            // Simulate a hard failure
            throw new RuntimeException("Third-party gateway returned 5xx");
        }

        // Success case
        return String.format("SUCCESS: payment %s of %.2f processed by third-party gateway", paymentId, amount);
    }
}
