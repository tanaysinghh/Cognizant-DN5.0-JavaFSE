package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.exception.EmployeeNotFoundException;
import com.cognizant.springlearn.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * HOL 4: Full CRUD on the in-memory employee list.
 * Throws EmployeeNotFoundException for missing ids.
 */
@Repository
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);
    private static final ArrayList<Employee> EMPLOYEE_LIST = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public EmployeeDao() {
        LOGGER.info("Start EmployeeDao init");
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("employee.xml")) {
            List<Employee> employees = (List<Employee>) context.getBean("employeeList", List.class);
            EMPLOYEE_LIST.clear();
            EMPLOYEE_LIST.addAll(employees);
        }
        LOGGER.info("Loaded {} employees", EMPLOYEE_LIST.size());
    }

    public List<Employee> getAllEmployees() {
        return EMPLOYEE_LIST;
    }

    public Employee getEmployee(int id) throws EmployeeNotFoundException {
        return EMPLOYEE_LIST.stream()
                .filter(e -> e.getId() != null && e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee with id " + id + " not found"));
    }

    public Employee addEmployee(Employee employee) {
        EMPLOYEE_LIST.add(employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            if (EMPLOYEE_LIST.get(i).getId().equals(employee.getId())) {
                EMPLOYEE_LIST.set(i, employee);
                return employee;
            }
        }
        throw new EmployeeNotFoundException(
                "Employee with id " + employee.getId() + " not found");
    }

    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        boolean removed = EMPLOYEE_LIST.removeIf(
                e -> e.getId() != null && e.getId().equals(id));
        if (!removed) {
            throw new EmployeeNotFoundException(
                    "Employee with id " + id + " not found");
        }
    }
}
