package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * HOL 1
 *  - Hands on 1: Bootstrap Spring Boot app + start/end logs in main()
 *  - Hands on 2: Load SimpleDateFormat bean from date-format.xml and parse a date
 *  - Hands on 3: Logging config in application.properties (see /resources)
 */
@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Start SpringLearnApplication.main()");
        SpringApplication.run(SpringLearnApplication.class, args);
        displayDate();
        LOGGER.info("End SpringLearnApplication.main()");
    }

    public static void displayDate() {
        LOGGER.info("Start displayDate()");
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("date-format.xml")) {

            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
            Date date = format.parse("31/12/2018");
            LOGGER.debug("Parsed date = {}", date);
            System.out.println("Parsed date: " + date);
        } catch (Exception e) {
            LOGGER.error("Failed to parse date", e);
        }
        LOGGER.info("End displayDate()");
    }
}
