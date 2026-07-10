# HOL 4 — POST / PUT / DELETE + Validation + Global Exception Handling

## What this covers

- REST URL naming guidelines applied throughout
- New HTTP methods:
    - `POST /countries` — add
    - `PUT /countries` — update
    - `DELETE /countries/{code}` — delete
    - same set for `/employees`
- Bean validation on all models: `@NotNull`, `@NotBlank`, `@Size(min,max)`, `@Min`, `@JsonFormat(pattern="dd/MM/yyyy")`
- `@Valid` on `@RequestBody`
- `EmployeeNotFoundException` — mapped to **404** via `@ResponseStatus`
- **`GlobalExceptionHandler`** — extends `ResponseEntityExceptionHandler`:
    - handles `MethodArgumentNotValidException` (validation failures → 400 with a `messages` list of field errors)
    - handles `HttpMessageNotReadableException` (e.g. passing a string in a numeric field → 400 with "Incorrect format for field 'id'")

## Run

```bash
cd spring-learn
mvn spring-boot:run
```

## Run the tests

```bash
mvn test
```

Runs:
- `CountryControllerTest.shouldReturnCountries` — happy path
- `CountryControllerTest.shouldRejectInvalidCountry` — validation error path

## Try it with curl

### Country CRUD
```bash
# GET all
curl http://localhost:8083/countries

# POST — valid
curl -H "Content-Type: application/json" -X POST \
  -d '{"code":"FR","name":"France"}' \
  http://localhost:8083/countries

# POST — invalid (empty fields) → 400 with error list
curl -i -H "Content-Type: application/json" -X POST \
  -d '{"code":"","name":""}' \
  http://localhost:8083/countries

# PUT
curl -H "Content-Type: application/json" -X PUT \
  -d '{"code":"IN","name":"Bharat"}' \
  http://localhost:8083/countries

# DELETE
curl -X DELETE http://localhost:8083/countries/JP
```

### Employee CRUD
```bash
# GET all
curl http://localhost:8083/employees

# GET one — 404 if not found (EmployeeNotFoundException + @ResponseStatus)
curl -i http://localhost:8083/employees/999

# PUT with wrong type on numeric field → triggers HttpMessageNotReadableException branch
curl -i -H "Content-Type: application/json" -X PUT \
  -d '{"id":"abc","name":"Test","salary":100,"permanent":true,"dateOfBirth":"01/01/2000"}' \
  http://localhost:8083/employees
# → { "message": "Incorrect format for field 'id'" }

# DELETE
curl -X DELETE http://localhost:8083/employees/1
```
