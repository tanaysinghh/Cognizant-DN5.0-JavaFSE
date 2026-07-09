package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.exception.EmployeeNotFoundException;
import com.cognizant.springlearn.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * HOL 3: Static employee list populated from employee.xml.
 * HOL 4: Add / update / delete methods.
 */
@Repository
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);
    private static final ArrayList<Employee> EMPLOYEE_LIST = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public EmployeeDao() {
        LOGGER.info("Start EmployeeDao init — loading employees from employee.xml");
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
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
    }

    public Employee addEmployee(Employee employee) {
        EMPLOYEE_LIST.add(employee);
        return employee;
    }

    // HOL 4: Update — throw EmployeeNotFoundException if not found
    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            if (EMPLOYEE_LIST.get(i).getId().equals(employee.getId())) {
                EMPLOYEE_LIST.set(i, employee);
                return employee;
            }
        }
        throw new EmployeeNotFoundException("Employee with id " + employee.getId() + " not found");
    }

    // HOL 4: Delete
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        boolean removed = EMPLOYEE_LIST.removeIf(e -> e.getId() != null && e.getId().equals(id));
        if (!removed) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
    }
}
