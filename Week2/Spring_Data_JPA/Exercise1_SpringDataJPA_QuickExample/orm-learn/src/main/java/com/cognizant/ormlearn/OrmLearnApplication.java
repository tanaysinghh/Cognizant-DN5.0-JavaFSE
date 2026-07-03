package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
    private static CountryService countryService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);

        LOGGER.info("Inside main");
        testGetAllCountries();
        testFindCountryByCode();
        testAddCountry();
    }

    // Mandatory: implement services for managing Country (list all)
    private static void testGetAllCountries() {
        LOGGER.info("Start - getAllCountries");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries={}", countries);
        LOGGER.info("End - getAllCountries");
    }

    // Mandatory: find a country based on country code
    private static void testFindCountryByCode() {
        LOGGER.info("Start - findCountryByCode");
        Country country = countryService.findCountryByCode("IN");
        LOGGER.debug("country={}", country);
        LOGGER.info("End - findCountryByCode");
    }

    // Mandatory: add a new country
    private static void testAddCountry() {
        LOGGER.info("Start - addCountry");
        Country country = new Country();
        country.setCode("FR");
        country.setName("France");
        countryService.addCountry(country);
        LOGGER.debug("Added country={}", country);
        LOGGER.info("End - addCountry");
    }
}
