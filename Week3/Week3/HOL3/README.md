# HOL 3 — Employee & Department REST Services (Controller / Service / Dao)

## What this covers

- Static Employee list defined in `employee.xml` with:
    - 3 Departments (Development, QA, Operations)
    - 4 Skills reused across employees (Java, Spring, Angular, React)
    - 4 Employees, each with a Department and multiple Skills
- **EmployeeDao** — reads `employee.xml` into a static `EMPLOYEE_LIST`
- **EmployeeService** (`@Service` + `@Transactional`) — delegates to Dao
- **EmployeeController** — `GET /employees`
- **DepartmentDao / Service / Controller** — `GET /departments`

## Run

```bash
cd spring-learn
mvn spring-boot:run
```

## Test in Postman / curl

```bash
curl http://localhost:8083/employees
curl http://localhost:8083/departments
```

Sample `/employees` response is JSON with each employee's department + skills (Jackson serializes nested beans automatically). Check the logs — you'll see `Start getAllEmployees()` and `Start getAllDepartments()` when hit.
