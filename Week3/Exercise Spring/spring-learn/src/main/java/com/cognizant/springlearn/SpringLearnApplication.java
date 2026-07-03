package com.cognizant.springlearn;

import com.cognizant.springlearn.model.Country;
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
        SpringApplication.run(SpringLearnApplication.class, args);
        LOGGER.info("Inside main");

        displayDate();
        displayCountry();
    }

    // Hands-on 2: load SimpleDateFormat bean from XML and parse a date
    public static void displayDate() {
        LOGGER.info("Start - displayDate");
        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
        try {
            Date date = format.parse("31/12/2018");
            LOGGER.debug("Parsed date = {}", date);
        } catch (Exception e) {
            LOGGER.error("Error parsing date: {}", e.getMessage());
        }
        LOGGER.info("End - displayDate");
    }

    // Load the Country bean from XML and print it
    public static void displayCountry() {
        LOGGER.info("Start - displayCountry");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("country", Country.class);
        LOGGER.debug("Country : {}", country);
        LOGGER.info("End - displayCountry");
    }
}
