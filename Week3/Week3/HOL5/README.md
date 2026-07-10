# HOL 5 — Spring Security + JWT Authentication

## What this covers

- Enable Spring Security → all URLs protected by default
- Two in-memory users: `admin/pwd` (ROLE_ADMIN), `user/pwd` (ROLE_USER)
- `BCryptPasswordEncoder` for storing hashed passwords
- `/authenticate` — HTTP Basic auth required; returns a fresh JWT for the caller
- `JwtAuthorizationFilter` — reads `Authorization: Bearer <token>`, verifies the signature, sets the security context
- Stateless session policy → every request must present its own token

## Modernization notes (Spring Boot 3 / Spring Security 6)

The hands-on doc references APIs **removed** in Spring Security 6. This project uses the current equivalents — same behaviour:

| Doc uses (deprecated)              | Code uses (Spring Boot 3)            |
| ---------------------------------- | ------------------------------------ |
| `WebSecurityConfigurerAdapter`     | `SecurityFilterChain` bean           |
| `antMatchers(...)`                 | `requestMatchers(...)`               |
| `Jwts.parser().setSigningKey(...)` | `Jwts.parser().verifyWith(key).build().parseSignedClaims(token)` |
| `javax.servlet.*`                  | `jakarta.servlet.*`                  |

## Run

```bash
cd spring-learn
mvn spring-boot:run
```

## Test the JWT flow

```bash
# 1. Without a token → 401 Unauthorized
curl -i http://localhost:8083/countries

# 2. With Basic auth on any endpoint OTHER than /authenticate → also 401
#    (protected endpoints demand JWT, not Basic)
curl -i -u user:pwd http://localhost:8083/countries

# 3. Get a fresh JWT from /authenticate using Basic
TOKEN=$(curl -s -u user:pwd http://localhost:8083/authenticate)
echo $TOKEN

# 4. Use the token to access any protected endpoint
curl -H "Authorization: Bearer $TOKEN" http://localhost:8083/countries
curl -H "Authorization: Bearer $TOKEN" http://localhost:8083/employees
curl -H "Authorization: Bearer $TOKEN" http://localhost:8083/departments

# 5. Tamper the token → 401
curl -i -H "Authorization: Bearer tampered.token.here" http://localhost:8083/countries
```

The `/hello` endpoint is left **public** so you can still hit it without a token — useful for quick smoke tests.

## Run tests

```bash
mvn test
```

The context-load test verifies the full security wiring boots correctly.
