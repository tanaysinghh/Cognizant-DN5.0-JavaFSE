package com.cognizant.springlearn.service;

import com.cognizant.springlearn.dao.EmployeeDao;
import com.cognizant.springlearn.exception.EmployeeNotFoundException;
import com.cognizant.springlearn.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start getAllEmployees()");
        return employeeDao.getAllEmployees();
    }

    @Transactional
    public Employee getEmployee(int id) throws EmployeeNotFoundException {
        return employeeDao.getEmployee(id);
    }

    @Transactional
    public Employee addEmployee(Employee employee) {
        return employeeDao.addEmployee(employee);
    }

    @Transactional
    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        return employeeDao.updateEmployee(employee);
    }

    @Transactional
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        employeeDao.deleteEmployee(id);
    }
}
