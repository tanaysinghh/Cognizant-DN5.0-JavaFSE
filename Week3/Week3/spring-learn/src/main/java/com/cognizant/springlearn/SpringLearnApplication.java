package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Start SpringLearnApplication.main()");
        SpringApplication.run(SpringLearnApplication.class, args);
        displayDate();
        LOGGER.info("End SpringLearnApplication.main()");
    }

    /**
     * HOL 1 (Hands on 2): Load SimpleDateFormat from Spring XML config
     * and parse a date string using it.
     */
    public static void displayDate() {
        LOGGER.info("Start displayDate()");
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("date-format.xml")) {
            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
            Date date = format.parse("31/12/2018");
            LOGGER.debug("Parsed date: {}", date);
            System.out.println("Parsed date: " + date);
        } catch (Exception e) {
            LOGGER.error("Error parsing date", e);
        }
        LOGGER.info("End displayDate()");
    }
}
