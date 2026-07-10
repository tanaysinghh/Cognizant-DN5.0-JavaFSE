package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);
    private static final ArrayList<Employee> EMPLOYEE_LIST = new ArrayList<>();

    /**
     * Constructor reads employee list from employee.xml and populates EMPLOYEE_LIST.
     */
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
}
