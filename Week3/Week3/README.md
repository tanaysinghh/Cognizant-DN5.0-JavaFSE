# Week3 — Spring Boot, Spring REST & JWT Hands-On

Five hands-on exercises, each in its own folder. Each `HOLx/spring-learn/` is an independent, runnable Spring Boot 3.2.5 Maven project.

Every HOL **builds on the previous one** — HOL 2 has HOL 1's code, HOL 3 has HOL 1 + 2, etc. If you want the final complete project, use **HOL 5**.

## Layout

```
Week3/
├── HOL1/  → Spring Boot bootstrap + SimpleDateFormat XML bean + logging
├── HOL2/  → Hello World REST + MockMvc test
├── HOL3/  → Employee & Department REST from XML (Controller/Service/Dao)
├── HOL4/  → POST/PUT/DELETE + @Valid + GlobalExceptionHandler
└── HOL5/  → Spring Security + JWT authentication
```

## Stack

- Java 17
- Spring Boot **3.2.5**
- Spring Security **6.x** (HOL 5)
- jjwt **0.12.5** (HOL 5)
- Maven

## Run any HOL

```bash
cd HOL<n>/spring-learn
mvn spring-boot:run
```

Every HOL runs on **port 8083**.

## What each HOL covers

| HOL | Focus                                                              |
| --- | ------------------------------------------------------------------ |
| 1   | `@SpringBootApplication`, `main()`, load `SimpleDateFormat` bean from `date-format.xml`, logging config (level + pattern) |
| 2   | `@RestController`, `@GetMapping`, MockMvc end-to-end test          |
| 3   | Employee/Department loaded from `employee.xml` via Dao → Service (`@Transactional`) → Controller; `GET /employees`, `GET /departments` |
| 4   | Full CRUD (`@Post/Put/DeleteMapping`); bean validation (`@Valid`, `@NotNull`, `@Size`, `@Min`, `@JsonFormat`); `EmployeeNotFoundException` with `@ResponseStatus`; `GlobalExceptionHandler` for validation + malformed JSON |
| 5   | Spring Security 6 with `SecurityFilterChain` bean; in-memory users; `/authenticate` returns JWT; `JwtAuthorizationFilter` verifies Bearer tokens |

Each HOL folder has its own README with run commands and test curls.

## Modernization note (Spring Boot 3)

The training docs reference several APIs that were removed in the Jakarta EE / Spring Security 6 migration. Everything still matches the *intent* of each exercise but uses the current-compilable equivalents:

| Doc                            | Code                             |
| ------------------------------ | -------------------------------- |
| `javax.validation.*`           | `jakarta.validation.*`           |
| `javax.servlet.*`              | `jakarta.servlet.*`              |
| `WebSecurityConfigurerAdapter` | `SecurityFilterChain` bean       |
| `antMatchers`                  | `requestMatchers`                |
| jjwt 0.11 API                  | jjwt 0.12 (`verifyWith`, `parseSignedClaims`) |
