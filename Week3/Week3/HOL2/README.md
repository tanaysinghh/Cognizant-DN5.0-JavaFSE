# HOL 2 — Hello World REST Service + MockMvc Test

## What this covers

- REST implementation with `@RestController` + `@GetMapping`
- `GET /hello` returns `"Hello World!!"`
- End-to-end MockMvc test (`@WebMvcTest`, `perform()`, `andExpect(status().isOk())`, `content()`)

## Run

```bash
cd spring-learn
mvn spring-boot:run
```

Then in a browser or Postman:
```
http://localhost:8083/hello
```

## Run the test

```bash
mvn test
```

The test asserts HTTP 200 + body `Hello World!!`.

## Try in browser DevTools

- Open Chrome → F12 → Network tab → hit `http://localhost:8083/hello`
- Click the request → observe **Request headers** and **Response headers** (Content-Type, User-Agent, etc.) — matches the RFC 7230 request/response format the doc walks through.
