# Exercise 3 — Spring Cloud API Gateway

Gateway routing to **customer-service** and **billing-service** with **rate limiting, caching, and path rewriting**.

## Services

| Service          | Port |
| ---------------- | ---- |
| api-gateway      | 9090 |
| customer-service | 8181 |
| billing-service  | 8182 |
| **redis**        | 6379 (required by rate limiter) |

## Prerequisite: Redis

Spring Cloud Gateway's `RequestRateLimiter` uses Redis as the token-bucket backend.

```bash
docker run -d --name redis -p 6379:6379 redis
```

If you don't want Redis, delete both `RequestRateLimiter` filter blocks in `application.yml` and remove the redis dependency from `pom.xml`.

## Run

```bash
# terminal 1
cd customer-service && mvn spring-boot:run

# terminal 2
cd billing-service && mvn spring-boot:run

# terminal 3
cd api-gateway && mvn spring-boot:run
```

## Test routing

```bash
# Customer routes (StripPrefix=1 strips /api)
curl http://localhost:9090/api/customers
curl http://localhost:9090/api/customers/1

# Billing routes
curl http://localhost:9090/api/bills
curl http://localhost:9090/api/bills/customer/1

# Path rewriting: /v1/customers/1 → forwarded as /customers/1
curl http://localhost:9090/v1/customers/1
```

## Test rate limiting

Customer route allows 5 req/sec with burst of 10.

```bash
# Hammer the endpoint
for i in {1..20}; do curl -s -o /dev/null -w "%{http_code} " http://localhost:9090/api/customers; done
# You'll see 200s followed by 429 (Too Many Requests) once the bucket empties
```

Billing route is stricter: 3/sec, burst 6.

## Test caching

The LocalResponseCache filter is enabled globally with 30s TTL. Successive GETs return quickly and the downstream service log shows only the first hit.

```bash
# First call hits customer-service
curl http://localhost:9090/api/customers/1
# Second call within 30s served from cache
curl http://localhost:9090/api/customers/1
```

## Filters at a glance

| Filter                | Effect                                              |
| --------------------- | --------------------------------------------------- |
| StripPrefix=1         | Removes first path segment (`/api` → gone)          |
| RewritePath           | Rewrites `/v1/customers/*` → `/customers/*`         |
| RequestRateLimiter    | Token bucket per IP, backed by Redis                |
| LocalResponseCache    | Caches GET responses in memory for 30s              |
