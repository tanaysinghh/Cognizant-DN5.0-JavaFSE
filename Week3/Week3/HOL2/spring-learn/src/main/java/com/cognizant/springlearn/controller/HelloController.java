package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HOL 2: Hello World REST service.
 * Method: GET
 * URL:    /hello
 * Response: "Hello World!!"
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.info("Start sayHello()");
        String response = "Hello World!!";
        LOGGER.info("End sayHello()");
        return response;
    }
}
