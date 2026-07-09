package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Country;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * HOL 4: Country CRUD demonstrating @GetMapping / @PostMapping / @PutMapping / @DeleteMapping
 * with @Valid input validation.
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    private static final List<Country> COUNTRIES = new ArrayList<>(List.of(
            new Country("US", "United States"),
            new Country("DE", "Germany"),
            new Country("IN", "India"),
            new Country("JP", "Japan")
    ));

    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.info("Start getAllCountries()");
        return COUNTRIES;
    }

    @GetMapping("/{code}")
    public Country getCountry(@PathVariable String code) {
        LOGGER.info("Start getCountry({})", code);
        return COUNTRIES.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Country addCountry(@RequestBody @Valid Country country) {
        LOGGER.info("Start addCountry()");
        LOGGER.debug("Country: {}", country);
        COUNTRIES.add(country);
        return country;
    }

    @PutMapping
    public Country updateCountry(@RequestBody @Valid Country country) {
        LOGGER.info("Start updateCountry({})", country.getCode());
        for (int i = 0; i < COUNTRIES.size(); i++) {
            if (COUNTRIES.get(i).getCode().equalsIgnoreCase(country.getCode())) {
                COUNTRIES.set(i, country);
                return country;
            }
        }
        return null;
    }

    @DeleteMapping("/{code}")
    public void deleteCountry(@PathVariable String code) {
        LOGGER.info("Start deleteCountry({})", code);
        COUNTRIES.removeIf(c -> c.getCode().equalsIgnoreCase(code));
    }
}
