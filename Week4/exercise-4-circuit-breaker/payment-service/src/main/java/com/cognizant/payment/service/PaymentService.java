package com.cognizant.payment.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private ThirdPartyPaymentClient thirdPartyClient;

    /**
     * Wrapped call. Order of decorators (outer → inner): Retry → CircuitBreaker → the actual call.
     * - Retry: attempts the call up to N times before giving up.
     * - CircuitBreaker: after failure threshold is breached, short-circuits calls for a wait period.
     * - fallbackMethod: invoked when the call still fails (either from an open circuit or exhausted retries).
     */
    @Retry(name = "paymentRetry")
    @CircuitBreaker(name = "paymentCB", fallbackMethod = "paymentFallback")
    public String makePayment(String paymentId, double amount) {
        log.info("Attempting payment: {} amount={}", paymentId, amount);
        return thirdPartyClient.processPayment(paymentId, amount);
    }

    // Fallback signature must match the wrapped method + a trailing Throwable.
    public String paymentFallback(String paymentId, double amount, Throwable ex) {
        log.warn("FALLBACK triggered for payment {} — reason: {}", paymentId, ex.getMessage());
        return String.format(
                "FALLBACK: payment %s of %.2f queued for retry (reason: %s)",
                paymentId, amount, ex.getClass().getSimpleName()
        );
    }
}
