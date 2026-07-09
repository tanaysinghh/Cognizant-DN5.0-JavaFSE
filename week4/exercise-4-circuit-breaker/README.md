# Exercise 4 — Resilient Payment Service (Circuit Breaker + Retry + Fallback)

A Payment service that calls a simulated slow, unreliable third-party gateway. Uses **Resilience4j** to protect itself.

## What's inside

- `ThirdPartyPaymentClient` — simulates the flaky external gateway (~60% failure rate, some slow calls).
- `PaymentService` — wraps the call with:
  - `@CircuitBreaker(name="paymentCB", fallbackMethod="paymentFallback")`
  - `@Retry(name="paymentRetry")`
- `CircuitBreakerMonitor` — hooks into event streams and logs every state transition (CLOSED → OPEN → HALF_OPEN → CLOSED) and every fallback.

## Circuit breaker settings (application.yml)

| Setting                   | Value           | Meaning |
| ------------------------- | --------------- | ------- |
| sliding-window-size       | 10 calls        | Track last 10 calls |
| failure-rate-threshold    | 50%             | Open circuit if ≥50% fail |
| wait-duration-in-open-state | 10s           | Stay OPEN for 10s |
| minimum-number-of-calls   | 5               | Need at least 5 samples |
| Retry max-attempts        | 3               | Retry up to 3 times |

## Run

```bash
cd payment-service && mvn spring-boot:run
```

## Test

```bash
# Hit the endpoint many times — some succeed, some fail, watch the fallback kick in
for i in {1..30}; do
  echo "call $i:"
  curl -s http://localhost:8083/payments/test
  echo
done
```

You'll see a mix of `SUCCESS: ...` and `FALLBACK: ...` responses. Watch the service logs — you'll see:

```
[CB:paymentCB] error — java.lang.RuntimeException: ...
[CB:paymentCB] state transition CLOSED → OPEN
[CB:paymentCB] call NOT permitted (circuit is OPEN)
[CB:paymentCB] state transition OPEN → HALF_OPEN
[CB:paymentCB] state transition HALF_OPEN → CLOSED
```

## Live monitoring endpoints

```bash
# Current state of all circuit breakers
curl http://localhost:8083/actuator/circuitbreakers

# Recent events (state changes, errors, successes)
curl http://localhost:8083/actuator/circuitbreakerevents

# Retry events
curl http://localhost:8083/actuator/retryevents

# Health includes circuit breaker status
curl http://localhost:8083/actuator/health
```

## How the states behave in practice

1. **CLOSED** — normal traffic, calls forwarded to the third party.
2. When failure rate crosses 50% over the sliding window → **OPEN**. All calls skip the third party and go straight to fallback for 10 seconds.
3. After 10s → **HALF_OPEN**. Allows 3 trial calls. If they mostly succeed → CLOSED. If they fail → OPEN again.

This is exactly the "circuit breaker + fallback logic" the exercise asks for.
