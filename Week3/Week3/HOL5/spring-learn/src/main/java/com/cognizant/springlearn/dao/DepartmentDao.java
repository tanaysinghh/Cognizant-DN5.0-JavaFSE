package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDao.class);
    private static final ArrayList<Department> DEPARTMENT_LIST = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public DepartmentDao() {
        LOGGER.info("Start DepartmentDao init");
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("employee.xml")) {
            List<Department> departments =
                    (List<Department>) context.getBean("departmentList", List.class);
            DEPARTMENT_LIST.clear();
            DEPARTMENT_LIST.addAll(departments);
        }
        LOGGER.info("Loaded {} departments", DEPARTMENT_LIST.size());
    }

    public List<Department> getAllDepartments() {
        return DEPARTMENT_LIST;
    }
}
