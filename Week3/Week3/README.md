# Week3 — Spring Boot, Spring REST & JWT Hands-On

All 5 hands-on exercises combined into **one unified `spring-learn` project**, matching how the training builds up incrementally.

## Stack

- Java 17
- Spring Boot **3.2.5**
- Spring Security **6.x**
- jjwt **0.12.5**
- Maven

## Modernization notes (Spring Boot 3)

The original hands-on doc references APIs that were **removed** in Spring Boot 3 / Spring Security 6. I migrated them without changing the exercise's intent:

| Doc uses (old)                         | Code uses (Spring Boot 3)                |
| -------------------------------------- | ---------------------------------------- |
| `javax.validation.*`                   | `jakarta.validation.*`                   |
| `javax.servlet.*`                      | `jakarta.servlet.*`                      |
| `WebSecurityConfigurerAdapter`         | `SecurityFilterChain` bean               |
| `antMatchers(...)`                     | `requestMatchers(...)`                   |
| `jjwt` old API (`Jwts.parser().setSigningKey(...)`) | `jjwt 0.12` (`Jwts.parser().verifyWith(key).build().parseSignedClaims(token)`) |

## What each HOL covers

### HOL 1 — Bootstrap + Spring Core + Logging
- `spring-learn` project created via Spring Initializr equivalents in `pom.xml`
- `date-format.xml` — `SimpleDateFormat` bean loaded from XML config
- `SpringLearnApplication.displayDate()` — retrieves the bean and parses `31/12/2018`
- Logging configured in `application.properties` (levels + custom pattern)

### HOL 2 — Hello World REST + MockMVC
- `HelloController` → `GET /hello` → `"Hello World!!"`
- `HelloControllerTest` uses `@WebMvcTest` + MockMvc to verify status + body

### HOL 3 — Employee + Department REST (Controller / Service / Dao)
- `employee.xml` — 4 employees, 3 departments, 4 shared skills loaded as Spring beans
- `EmployeeDao` reads `employeeList` from XML into `EMPLOYEE_LIST`
- `EmployeeService` (`@Service` + `@Transactional`), `EmployeeController` → `GET /employees`, `GET /employees/{id}`
- Same three-layer setup for `Department` → `GET /departments`

### HOL 4 — POST / PUT / DELETE + Validation + Global Exception Handling
- Bean validation on `Employee`, `Department`, `Skill`, `Country`
    - `@NotNull`, `@NotBlank`, `@Size(min, max)`, `@Min(0)`, `@JsonFormat(pattern="dd/MM/yyyy")`
- `CountryController` → full CRUD (`GET`, `POST`, `PUT`, `DELETE`) following REST URL conventions
- `EmployeeController` extended with `POST`, `PUT`, `DELETE` — all use `@Valid`
- `EmployeeNotFoundException` mapped to **404** via `@ResponseStatus`
- `GlobalExceptionHandler` (extends `ResponseEntityExceptionHandler`):
    - Catches `MethodArgumentNotValidException` → returns 400 with a `messages` array of field errors
    - Catches `HttpMessageNotReadableException` for cases like passing a string in a numeric `id` field

### HOL 5 — Spring Security + JWT
- `SecurityConfig` — 2 in-memory users: `admin/pwd` (ROLE_ADMIN), `user/pwd` (ROLE_USER)
- `/authenticate` — Basic auth required, returns a signed JWT
- `JwtAuthorizationFilter` — reads `Authorization: Bearer <token>`, verifies signature, sets security context
- Stateless session policy — every request must present a token
- `/hello` intentionally left open for easy demo

## Run it

```bash
cd spring-learn
mvn spring-boot:run
```

The app starts on **port 8083** (matches HOL 2's sample URL).

## Test it (curl commands)

### HOL 1 — check console output
Watch startup logs — you'll see `Parsed date: Mon Dec 31 00:00:00 ... 2018` from `displayDate()`.

### HOL 2 — Hello (public)
```bash
curl http://localhost:8083/hello
# → Hello World!!
```

### HOL 3 — Employee / Department (needs JWT — see HOL 5 first)
```bash
# 1. Get a token
TOKEN=$(curl -s -u user:pwd http://localhost:8083/authenticate)
echo $TOKEN

# 2. Use it
curl -H "Authorization: Bearer $TOKEN" http://localhost:8083/employees
curl -H "Authorization: Bearer $TOKEN" http://localhost:8083/employees/1
curl -H "Authorization: Bearer $TOKEN" http://localhost:8083/departments
```

### HOL 4 — Country CRUD + validation

```bash
# GET all
curl -H "Authorization: Bearer $TOKEN" http://localhost:8083/countries

# POST — valid
curl -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
     -X POST -d '{"code":"FR","name":"France"}' \
     http://localhost:8083/countries

# POST — invalid (empty fields) → 400 with error list from GlobalExceptionHandler
curl -i -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
     -X POST -d '{"code":"","name":""}' \
     http://localhost:8083/countries

# PUT
curl -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
     -X PUT -d '{"code":"IN","name":"Bharat"}' \
     http://localhost:8083/countries

# DELETE
curl -H "Authorization: Bearer $TOKEN" -X DELETE http://localhost:8083/countries/JP
```

Employee update with malformed id (string instead of number) → triggers the `HttpMessageNotReadableException` branch:
```bash
curl -i -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
     -X PUT -d '{"id":"abc","name":"Test","salary":100,"permanent":true,"dateOfBirth":"01/01/2000"}' \
     http://localhost:8083/employees
# → 400 { "message": "Incorrect format for field 'id'" }
```

### HOL 5 — JWT flow

```bash
# Without a token → 401
curl -i http://localhost:8083/countries

# With Basic auth on any protected endpoint (other than /authenticate) → 401
# because non-/authenticate endpoints require JWT, not Basic
curl -i -u user:pwd http://localhost:8083/countries

# Get JWT
curl -s -u user:pwd http://localhost:8083/authenticate

# Use it
curl -H "Authorization: Bearer <paste-token>" http://localhost:8083/countries

# Tamper with token → 401
curl -i -H "Authorization: Bearer tampered.token.here" http://localhost:8083/countries
```

### Run unit tests

```bash
mvn test
```

Runs:
- `HelloControllerTest` — HOL 2
- `CountryControllerTest` — HOL 4 (both happy path + validation error path)

## Project layout

```
Week3/
└── spring-learn/
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/cognizant/springlearn/
        │   │   ├── SpringLearnApplication.java     # HOL 1 (main + displayDate)
        │   │   ├── controller/
        │   │   │   ├── HelloController.java        # HOL 2
        │   │   │   ├── EmployeeController.java     # HOL 3+4
        │   │   │   ├── DepartmentController.java   # HOL 3
        │   │   │   └── CountryController.java      # HOL 4
        │   │   ├── service/
        │   │   │   ├── EmployeeService.java
        │   │   │   └── DepartmentService.java
        │   │   ├── dao/
        │   │   │   ├── EmployeeDao.java
        │   │   │   └── DepartmentDao.java
        │   │   ├── model/
        │   │   │   ├── Employee.java               # HOL 4 validation
        │   │   │   ├── Department.java
        │   │   │   ├── Skill.java
        │   │   │   └── Country.java
        │   │   ├── exception/
        │   │   │   ├── EmployeeNotFoundException.java
        │   │   │   └── GlobalExceptionHandler.java # HOL 4
        │   │   └── security/                        # HOL 5
        │   │       ├── SecurityConfig.java
        │   │       ├── AuthController.java
        │   │       ├── JwtAuthorizationFilter.java
        │   │       └── JwtKeyProvider.java
        │   └── resources/
        │       ├── application.properties
        │       ├── date-format.xml                 # HOL 1
        │       └── employee.xml                    # HOL 3
        └── test/java/com/cognizant/springlearn/
            ├── HelloControllerTest.java             # HOL 2
            ├── CountryControllerTest.java           # HOL 4
            └── TestSecurityConfig.java
```
