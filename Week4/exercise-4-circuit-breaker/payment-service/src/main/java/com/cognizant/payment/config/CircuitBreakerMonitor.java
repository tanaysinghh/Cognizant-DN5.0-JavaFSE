package com.cognizant.payment.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Hooks into Resilience4j event streams and logs every state transition
 * (CLOSED → OPEN → HALF_OPEN → CLOSED) and every fallback trigger.
 * This is what the exercise means by "log and monitor fallback events".
 */
@Component
public class CircuitBreakerMonitor {

    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerMonitor.class);

    @Autowired
    private CircuitBreakerRegistry registry;

    @PostConstruct
    public void registerListeners() {
        registry.getAllCircuitBreakers().forEach(cb -> {
            cb.getEventPublisher()
                    .onStateTransition(event ->
                            log.warn("[CB:{}] state transition {} → {}",
                                    cb.getName(),
                                    event.getStateTransition().getFromState(),
                                    event.getStateTransition().getToState()))
                    .onError(event ->
                            log.error("[CB:{}] error — {}", cb.getName(), event.getThrowable().toString()))
                    .onCallNotPermitted(event ->
                            log.warn("[CB:{}] call NOT permitted (circuit is OPEN)", cb.getName()))
                    .onSuccess(event ->
                            log.info("[CB:{}] successful call in {} ms", cb.getName(), event.getElapsedDuration().toMillis()));
        });
    }
}
