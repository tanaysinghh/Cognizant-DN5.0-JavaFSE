package com.cognizant.springlearn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * HOL 4: Custom exception mapped to HTTP 404 via @ResponseStatus.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
