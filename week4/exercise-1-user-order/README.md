# Exercise 1 — User & Order Management System

Two microservices communicating via **OpenFeign**.

## Services

| Service       | Port | DB          |
| ------------- | ---- | ----------- |
| user-service  | 8081 | H2 (userdb) |
| order-service | 8082 | H2 (orderdb)|

Both come pre-seeded with data. MySQL config is available (commented) in `application.properties`.

## Run

```bash
# terminal 1
cd user-service && mvn spring-boot:run

# terminal 2
cd order-service && mvn spring-boot:run
```

## Test

```bash
# List users
curl http://localhost:8081/users

# List orders
curl http://localhost:8082/orders

# Get order 1 with user info (order-service calls user-service via Feign)
curl http://localhost:8082/orders/1

# Create a new order (order-service validates the user exists via Feign)
curl -X POST http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"product":"Mouse","quantity":1,"price":800.00}'

# Orders by user
curl http://localhost:8082/orders/user/1
```

## H2 consoles

- User DB: http://localhost:8081/h2-console (JDBC URL: `jdbc:h2:mem:userdb`)
- Order DB: http://localhost:8082/h2 (JDBC URL: `jdbc:h2:mem:orderdb`)

## Notes

- Order service uses `@FeignClient(name="user-service", url="...")` — a **URL-based** Feign client since this exercise doesn't require Eureka. If you want service discovery, add Eureka client + drop the `url` attribute.
- Requirements satisfied: REST APIs ✓, service-to-service via OpenFeign ✓, JPA/DB-backed storage ✓.
