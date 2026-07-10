package com.cognizant.springlearn.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HOL 4: Global exception handler.
 *
 *  - MethodArgumentNotValidException → @Valid failures (missing fields, wrong sizes, etc.)
 *      returns 400 with a "messages" list of field errors.
 *
 *  - HttpMessageNotReadableException → handles cases like passing "abc" for a numeric field,
 *      which fails during JSON parsing (before @Valid runs).
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", "Bad Request");

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.add(err.getField() + ": " + err.getDefaultMessage()));
        body.put("messages", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");

        if (ex.getCause() instanceof InvalidFormatException cause) {
            for (InvalidFormatException.Reference reference : cause.getPath()) {
                body.put("message", "Incorrect format for field '" + reference.getFieldName() + "'");
            }
        } else {
            body.put("message", "Malformed JSON in request body");
        }

        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }
}
