# HOL 1 — Spring Boot Bootstrap, Spring Core, Logging

## What this covers

- **Hands on 1** — Create Spring Web project via Spring Initializr equivalents (pom.xml with `spring-boot-starter-web` + DevTools), start/end log in `main()`
- **Hands on 2** — `SimpleDateFormat` bean defined in `date-format.xml` and loaded via `ClassPathXmlApplicationContext` in `displayDate()`
- **Hands on 3** — Logging configured in `application.properties` — level per package + custom console pattern

## Run

```bash
cd spring-learn
mvn spring-boot:run
```

App starts on **:8083**. Watch the console — you'll see:

```
... Start SpringLearnApplication.main()
... Start displayDate()
Parsed date: Mon Dec 31 00:00:00 IST 2018
... End displayDate()
... End SpringLearnApplication.main()
```

## Files of interest

| File | Purpose |
| ---- | ------- |
| `SpringLearnApplication.java` | main() + displayDate() |
| `src/main/resources/date-format.xml` | Spring bean config (Hands on 2) |
| `src/main/resources/application.properties` | Logging config (Hands on 3) |
